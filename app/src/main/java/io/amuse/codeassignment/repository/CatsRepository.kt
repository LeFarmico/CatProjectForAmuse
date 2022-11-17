package io.amuse.codeassignment.repository

import io.amuse.codeassignment.domain.impl.CatsApiImpl
import io.amuse.codeassignment.domain.model.Cat
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow

class CatsRepository {

    suspend fun fetchCats(): Flow<List<Cat>> = channelFlow {
        val api = CatsApiImpl()
        val cats = (0..50).map {
            async {
                api.getCat()
            }
        }.toTypedArray()

        // *cats unpacks Array to vararg
        val response = awaitAll(*cats)
        trySend(response)
    }
}
