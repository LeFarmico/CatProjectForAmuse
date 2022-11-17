package io.amuse.codeassignment.repository

import io.amuse.codeassignment.domain.api.CatsApi
import io.amuse.codeassignment.domain.model.Cat
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow

class CatsRepository {

    suspend fun fetchCats(): Flow<List<Cat>> = channelFlow {
        val cats = (0..50).map {
            async {
                CatsApi.getCat()
            }
        }.toTypedArray()

        // *cats unpacks Array to vararg
        val response = awaitAll(*cats)
        trySend(response)
    }
}
