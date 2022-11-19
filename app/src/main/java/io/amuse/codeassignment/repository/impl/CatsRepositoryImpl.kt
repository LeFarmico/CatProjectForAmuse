package io.amuse.codeassignment.repository.impl

import io.amuse.codeassignment.domain.api.CatsApi
import io.amuse.codeassignment.domain.model.CatModel
import io.amuse.codeassignment.domain.model.NetworkResponse
import io.amuse.codeassignment.repository.api.CatsRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class CatsRepositoryImpl @Inject constructor(
    private val api: CatsApi
) : CatsRepository {

    override suspend fun fetchCats(
        onStart: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<List<CatModel>> = channelFlow {
        val cats = (0..50).map {
            async {
                when (val catResponse = api.getCat()) {
                    is NetworkResponse.Error -> throw (catResponse.throwable) // handle API request errors
                    is NetworkResponse.Exception -> throw (catResponse.throwable) // handle user errors
                    is NetworkResponse.Success -> catResponse.data
                }
            }
        }.toTypedArray()

        // *cats unpacks Array to vararg
        val response = awaitAll(*cats)
        trySend(response)
    }
        .onStart { onStart() }
        .catch { onError(it.message) }
}
