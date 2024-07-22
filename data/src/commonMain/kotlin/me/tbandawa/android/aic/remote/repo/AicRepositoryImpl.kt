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
import me.tbandawa.android.aic.core.ArtworksResults
import me.tbandawa.android.aic.domain.models.Artwork
import me.tbandawa.android.aic.domain.repository.AicRepository
import me.tbandawa.android.aic.remote.api.AicApi
import me.tbandawa.android.aic.remote.mapper.ArtworkMapper
import me.tbandawa.android.aic.remote.responses.ErrorResponse

class AicRepositoryImpl(
    private val api: AicApi,
    private val coroutineDispatcher: CoroutineDispatcher,
    private val artworkMapper: ArtworkMapper
): AicRepository {

    override fun getArtWorks(): Flow<PagingData<Artwork>> {
        return Pager(
            config = PagingConfig(pageSize = 10, prefetchDistance = 2),
            pagingSourceFactory = { ArtworkPagingSource(api, artworkMapper) }
        ).flow.flowOn(coroutineDispatcher)
    }

    override suspend fun getArtworks(page: Int): Flow<ArtworksResults<List<Artwork>>> = flow {
        emit(ArtworksResults.Loading)
        emit(handleApiCall {
            artworkMapper.mapResponseToModels(api.getArtworks(page))
        })
    }.flowOn(coroutineDispatcher)

    override suspend fun getArtwork(id: Int): Flow<ArtworksResults<Artwork>> = flow {
        emit(ArtworksResults.Loading)
        emit(handleApiCall {
            artworkMapper.mapResponseToModel(api.getArtwork(id))
        })
    }.flowOn(coroutineDispatcher)
}

suspend fun <T> handleApiCall(
    apiCall: suspend () -> T
): ArtworksResults<T> {
    return try {
        val response = apiCall()
        ArtworksResults.Success(response)
    } catch (e: ResponseException) {
        ArtworksResults.Error(ErrorResponse(e.response.status.value, e.response.status.description, "Invalid server response"))
    } catch (e: ClientRequestException) {
        ArtworksResults.Error(ErrorResponse(e.response.status.value, e.response.status.description, "Server could not process your request"))
    } catch (e: ServerResponseException) {
        ArtworksResults.Error(ErrorResponse(e.response.status.value, e.response.status.description, "Invalid operation. Unable to complete task"))
    } catch (e: IOException) {
        ArtworksResults.Error(ErrorResponse(500, "Connection Error", "Server unreachable. Please check your internet connection and try again"))
    } catch (e: Exception) {
        ArtworksResults.Error(ErrorResponse(500, "Error Occurred", "Unexpected error occurred. Try again"))
    }
}