package io.amuse.codeassignment.domain.impl

import io.amuse.codeassignment.domain.CatsURL
import io.amuse.codeassignment.domain.api.CatsApi
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
        val timeout: Long = 5_000
        return try {
            // stopping of recursion in case of impossibility to find correct mimetype
            withTimeout(timeout) {
                val response = client.get(CatsURL.URL_JSON)

                // filter by mimetype, if it is not image/jpeg then trying to get another cat
                if ((response.body() as CatModel).mimeType == "image/jpeg") {
                    NetworkResponse.Success(response.body())
                } else {
                    getJpegCat()
                }
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
