package io.amuse.codeassignment.domain.impl

import io.amuse.codeassignment.domain.CatsURL
import io.amuse.codeassignment.domain.api.CatsApi
import io.amuse.codeassignment.domain.model.CatCountModel
import io.amuse.codeassignment.domain.model.CatModel
import io.amuse.codeassignment.domain.model.NetworkResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.withTimeout
import java.nio.channels.UnresolvedAddressException
import javax.inject.Inject

class CatsApiImpl @Inject constructor(private val client: HttpClient) : CatsApi {

    override suspend fun getJpegCat(): NetworkResponse<CatModel> {
        val timeout: Long = 10_000
        // stopping of recursion in case of impossibility to find correct mimetype
        return withTimeout(timeout) {
            try {
                val response = client.get(CatsURL.URL_JSON)

                // filter by mimetype, if it is not image/jpeg then trying to get another cat
                if ((response.body() as CatModel).mimeType == "image/jpeg") {
                    NetworkResponse.Success(response.body())
                } else {
                    getJpegCat()
                }
            } catch (e: ResponseException) {
                NetworkResponse.Error(
                    code = e.response.status.value,
                    message = e.message,
                    throwable = e
                )
            } catch (e: UnresolvedAddressException) {
                NetworkResponse.Exception(e)
            } catch (e: CancellationException) {
                NetworkResponse.Exception(
                    CancellationException(
                        "Cats with mimetype: image/jpeg haven't found for $timeout millis"
                    )
                )
            }
        }
    }

    override suspend fun getCatsCount(): NetworkResponse<CatCountModel> {
        return try {
            val response = client.get(CatsURL.URL + "/api/count")
            val catsCount = response.body() as CatCountModel
            NetworkResponse.Success(catsCount)
        } catch (e: ResponseException) {
            NetworkResponse.Error(
                code = e.response.status.value,
                message = e.message,
                throwable = e
            )
        } catch (e: UnresolvedAddressException) {
            NetworkResponse.Exception(e)
        }
    }
}
