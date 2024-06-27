package me.tbandawa.android.aic.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import me.tbandawa.android.aic.lifecycle.ArtworksState
import me.tbandawa.android.aic.remote.api.AicApi
import me.tbandawa.android.aic.remote.api.handleApiCall
import me.tbandawa.android.aic.remote.responses.Artwork
import me.tbandawa.android.aic.remote.responses.ArtworkResponse

class AicRepositoryImpl(
    private val api: AicApi,
    private val coroutineDispatcher: CoroutineDispatcher
): AicRepository {

    override fun getArtWorks(): Flow<PagingData<Artwork>> {
        return Pager(
            config = PagingConfig(pageSize = 10, prefetchDistance = 2),
            pagingSourceFactory = { ArtworkPagingSource(api) }
        ).flow
    }

    override suspend fun getArtwork(id: Int): Flow<ArtworksState<ArtworkResponse>> = flow {
        emit(ArtworksState.Loading)
        emit(handleApiCall {
            api.getArtwork(id)
        })
    }.flowOn(coroutineDispatcher)
}