package me.tbandawa.android.aic.remote.repo

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import me.tbandawa.android.aic.core.ArtworksResults
import me.tbandawa.android.aic.remote.responses.Artwork
import me.tbandawa.android.aic.remote.responses.ArtworkResponse
import me.tbandawa.android.aic.remote.responses.ArtworksResponse

interface AicRepository {
    fun getArtWorks(): Flow<PagingData<Artwork>>
    suspend fun getArtworks(page: Int): Flow<ArtworksResults<ArtworksResponse>>
    suspend fun getArtwork(id: Int): Flow<ArtworksResults<ArtworkResponse>>
}