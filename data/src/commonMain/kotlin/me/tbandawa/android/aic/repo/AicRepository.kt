package me.tbandawa.android.aic.repo

import app.cash.paging.PagingData
import kotlinx.coroutines.flow.Flow
import me.tbandawa.android.aic.lifecycle.ArtworksState
import me.tbandawa.android.aic.remote.responses.Artwork
import me.tbandawa.android.aic.remote.responses.ArtworkResponse

interface AicRepository {
    fun getArtWorks(): Flow<PagingData<Artwork>>
    suspend fun getArtwork(id: Int): Flow<ArtworksState<ArtworkResponse>>
}