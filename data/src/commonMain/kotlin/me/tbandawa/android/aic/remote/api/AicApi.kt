package me.tbandawa.android.aic.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.errors.IOException
import kotlinx.serialization.json.Json
import me.tbandawa.android.aic.lifecycle.ArtworksState
import me.tbandawa.android.aic.remote.responses.ArtworkResponse
import me.tbandawa.android.aic.remote.responses.ArtworksResponse
import me.tbandawa.android.aic.remote.responses.ErrorResponse

class AicApi {

    companion object {
        const val BASE_URL = "https://api.artic.edu/api/v1/artworks"
        const val PAGE = "page"
        const val LIMIT = "limit"
    }

    private val httpClient = HttpClient {
        expectSuccess = true
        install(HttpTimeout) {
            requestTimeoutMillis = 5000L
            connectTimeoutMillis = 5000L
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.BODY
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

suspend fun <T> handleApiCall(
    apiCall: suspend () -> T
): ArtworksState<T> {
    return try {
        val response = apiCall()
        ArtworksState.Success(response)
    } catch (e: ResponseException) {
        ArtworksState.Error(ErrorResponse(e.response.status.value, e.response.status.description, "Invalid server response"))
    } catch (e: ClientRequestException) {
        ArtworksState.Error(ErrorResponse(e.response.status.value, e.response.status.description, "Server could not process your request"))
    } catch (e: ServerResponseException) {
        ArtworksState.Error(ErrorResponse(e.response.status.value, e.response.status.description, "Invalid operation. Unable to complete task"))
    } catch (e: IOException) {
        ArtworksState.Error(ErrorResponse(500, "Connection Error", "Server unreachable. Please check your internet connection and try again"))
    } catch (e: Exception) {
        ArtworksState.Error(ErrorResponse(500, "Error Occurred", "Unexpected error occured. Try again"))
    }
}
