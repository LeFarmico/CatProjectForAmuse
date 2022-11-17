package io.amuse.codeassignment.domain.impl

import io.amuse.codeassignment.domain.api.CatsApi
import io.amuse.codeassignment.domain.model.Cat
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class CatsApiImpl(
    private val client: HttpClient = CatsApi.client // TODO change it to DI
) : CatsApi {

    override suspend fun getCat(): Cat {
        return CatsApi.client.get("https://cataas.com/cat?json=true").body()
    }
}
