package io.amuse.codeassignment.domain.api

import io.amuse.codeassignment.domain.model.Cat
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class CatsApi {
    companion object {
        val client = HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }

        }

        suspend fun getCat(): Cat {
            return client.get("https://cataas.com/cat?json=true").body()
        }
    }
}