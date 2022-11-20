package io.amuse.codeassignment.repository.impl

import io.amuse.codeassignment.domain.api.CatsApi
import io.amuse.codeassignment.domain.model.NetworkResponse
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
        val cats = (0..50).map {
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
            it.printStackTrace()
            onError(it.message)
        }

    override suspend fun fetchCatsCount(): Flow<Int> = channelFlow {
        when (val networkResponse = api.getCatsCount()) {
            is NetworkResponse.Error -> throw (networkResponse.throwable) // handle API request errors
            is NetworkResponse.Exception -> throw (networkResponse.throwable) // handle user errors
            is NetworkResponse.Success -> trySend(networkResponse.data.count)
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
