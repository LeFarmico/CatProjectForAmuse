package io.amuse.codeassignment.repository.impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import io.amuse.codeassignment.domain.api.CatsApi
import io.amuse.codeassignment.domain.model.NetworkResponse
import io.amuse.codeassignment.pagination.CatsSource
import io.amuse.codeassignment.repository.api.CatsRepository
import io.amuse.codeassignment.repository.model.CatViewDataModel
import io.amuse.codeassignment.repository.model.mapper.toViewData
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class CatsRepositoryImpl @Inject constructor(
    private val api: CatsApi
) : CatsRepository {

    override suspend fun fetchCats(
        onStart: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<List<CatViewDataModel>> = channelFlow {
        val cats = (0..19).map {
            async {
                when (val networkResponse = api.getJpegCat()) {
                    is NetworkResponse.Error -> throw (networkResponse.throwable) // handle API request errors
                    is NetworkResponse.Exception -> throw (networkResponse.throwable) // handle user errors
                    is NetworkResponse.Success -> networkResponse.data.toViewData() // map model to viewData
                }
            }
        }.toTypedArray()

        // *cats unpacks Array to vararg
        val response = awaitAll(*cats)
        trySend(response)
    }
        .onStart { onStart() }
        .catch {
            onError(it.message)
        }

    override suspend fun fetchCatsCount(onError: (String?) -> Unit): Flow<Int> = channelFlow {
        when (val networkResponse = api.getCatsCount()) {
            is NetworkResponse.Error -> throw (networkResponse.throwable) // handle API request errors
            is NetworkResponse.Exception -> throw (networkResponse.throwable) // handle user errors
            is NetworkResponse.Success -> trySend(networkResponse.data.count)
        }
    }.catch {
        onError(it.message)
    }

    override suspend fun fetchPagingCats(
        onError: (String?) -> Unit
    ): Flow<PagingData<CatViewDataModel>> {
        return Pager(PagingConfig(pageSize = 20)) { CatsSource(api) }
            .flow
            .catch {
                onError(it.message)
            }
    }

    private fun String.dateTimeToViewData(): String? {
        return try {
            this.removeRange(10, this.length)
        } catch (e: IndexOutOfBoundsException) {
            null
        }
    }
}
