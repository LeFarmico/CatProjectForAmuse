package io.amuse.codeassignment.repository

import io.amuse.codeassignment.domain.api.CatsApi
import io.amuse.codeassignment.domain.model.Cat
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import javax.inject.Inject

class CatsRepository @Inject constructor(
    private val api: CatsApi
) {

    suspend fun fetchCats(): Flow<List<Cat>> = channelFlow {
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
