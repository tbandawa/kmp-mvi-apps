package me.tbandawa.android.aic.remote.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import me.tbandawa.android.aic.lifecycle.ArtworksState
import me.tbandawa.android.aic.remote.api.AicApi
import me.tbandawa.android.aic.remote.responses.Artwork
import me.tbandawa.android.aic.remote.responses.ArtworkResponse
import me.tbandawa.android.aic.remote.responses.ErrorResponse

class AicRepositoryImpl(
    private val api: AicApi,
    private val coroutineDispatcher: CoroutineDispatcher
): AicRepository {

    override fun getArtWorks(): Flow<PagingData<Artwork>> {
        return Pager(
            config = PagingConfig(pageSize = 10, prefetchDistance = 2),
            pagingSourceFactory = { ArtworkPagingSource(api) }
        ).flow.flowOn(coroutineDispatcher)
    }

    override suspend fun getArtwork(id: Int): Flow<ArtworksState<ArtworkResponse>> = flow {
        emit(ArtworksState.Loading)
        emit(handleApiCall {
            api.getArtwork(id)
        })
    }.flowOn(coroutineDispatcher)
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