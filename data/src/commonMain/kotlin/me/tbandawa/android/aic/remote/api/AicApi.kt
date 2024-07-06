package me.tbandawa.android.aic.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import me.tbandawa.android.aic.remote.responses.ArtworkResponse
import me.tbandawa.android.aic.remote.responses.ArtworksResponse

class AicApi {

    companion object {
        const val BASE_URL = "https://api.artic.edu/api/v1/artworks"
        const val PAGE = "page"
    }

    private val httpClient = HttpClient {
        expectSuccess = true
        install(HttpTimeout) {
            requestTimeoutMillis = 5000L
            connectTimeoutMillis = 5000L
        }
        install(ContentNegotiation) {
            json(Json {
                isLenient = true
                encodeDefaults = true
                ignoreUnknownKeys = true
                useAlternativeNames = true
            })
        }
    }

    suspend fun getArtworks(page: Int): ArtworksResponse {
        return httpClient.get {
            url(BASE_URL)
            parameter(PAGE, page)
            contentType(ContentType.Application.Json)
        }.body()
    }

    suspend fun getArtwork(id: Int): ArtworkResponse {
        return httpClient.get {
            url("$BASE_URL/$id")
            contentType(ContentType.Application.Json)
        }.body()
    }
}
