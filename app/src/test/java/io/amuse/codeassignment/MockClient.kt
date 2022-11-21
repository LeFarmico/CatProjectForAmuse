package io.amuse.codeassignment

import android.util.Log
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.statement.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

object MockClient {

    fun client() = HttpClient(CIO) {
        engine {
            requestTimeout = 5000
            endpoint.connectTimeout = 1000
            endpoint.connectAttempts = 2
        }
        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                }
            )
        }
        install(Logging) {
            level = LogLevel.ALL
            logger = object : Logger {
                override fun log(message: String) {
                    Log.v("Logger Ktor =>", message)
                }
            }
        }
        HttpResponseValidator {
            validateResponse { response: HttpResponse ->
                val statusCode = response.status.value

                val exceptionMessage = when (statusCode) {
                    in 300..399 -> ""
                    in 400..499 -> ""
                    in 500..599 -> ""
                    else -> ""
                }

                when (statusCode) {
                    in 300..399 -> throw RedirectResponseException(response, exceptionMessage)
                    in 400..499 -> throw ClientRequestException(response, exceptionMessage)
                    in 500..599 -> throw ServerResponseException(response, exceptionMessage)
                }

                if (statusCode >= 600) {
                    throw ResponseException(response, exceptionMessage)
                }
            }

            handleResponseExceptionWithRequest { cause, _ ->
                throw cause
            }
        }
    }
}