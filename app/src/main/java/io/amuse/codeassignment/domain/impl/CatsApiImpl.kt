package io.amuse.codeassignment.domain.impl

import io.amuse.codeassignment.domain.api.CatsApi
import io.amuse.codeassignment.domain.model.CatModel
import io.amuse.codeassignment.domain.model.NetworkResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import java.nio.channels.UnresolvedAddressException
import javax.inject.Inject

class CatsApiImpl @Inject constructor(private val client: HttpClient) : CatsApi {

    override suspend fun getCat(): NetworkResponse<CatModel> {
        return try {
            val response = client.get("https://cataas.com/cat?json=true")
            NetworkResponse.Success(response.body())
        } catch (e: ResponseException) {
            NetworkResponse.Error(
                code = e.response.status.value,
                message = e.message ?: "",
                throwable = e
            )
        } catch (e: UnresolvedAddressException) {
            NetworkResponse.Exception(e)
        }
    }
}
