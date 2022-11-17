package io.amuse.codeassignment.domain.impl

import io.amuse.codeassignment.domain.api.CatsApi
import io.amuse.codeassignment.domain.model.Cat
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import javax.inject.Inject

class CatsApiImpl @Inject constructor(private val client: HttpClient) : CatsApi {

    override suspend fun getCat(): Cat {
        return client.get("https://cataas.com/cat?json=true").body()
    }
}
