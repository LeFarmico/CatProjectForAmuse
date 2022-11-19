package io.amuse.codeassignment.injection

import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.statement.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    @Singleton
    fun provideHttpClient() = HttpClient(CIO) {
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

                when (statusCode) {
                    in 300..399 -> throw RedirectResponseException(response, NO_RESPONSE_TEXT)
                    in 400..499 -> throw ClientRequestException(response, NO_RESPONSE_TEXT)
                    in 500..599 -> throw ServerResponseException(response, NO_RESPONSE_TEXT)
                }

                if (statusCode >= 600) {
                    throw ResponseException(response, NO_RESPONSE_TEXT)
                }
            }

            handleResponseExceptionWithRequest { cause, _ ->
                throw cause
            }
        }
    }

    companion object {
        private const val NO_RESPONSE_TEXT: String = "<no response text provided>"
    }
}
