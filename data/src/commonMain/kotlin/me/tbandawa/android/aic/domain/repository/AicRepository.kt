package me.tbandawa.android.aic.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import me.tbandawa.android.aic.core.ArtworksResults
import me.tbandawa.android.aic.domain.models.Artwork

interface AicRepository {
    fun getArtWorks(): Flow<PagingData<Artwork>>
    suspend fun getArtworks(page: Int): Flow<ArtworksResults<List<Artwork>>>
    suspend fun getArtwork(id: Int): Flow<ArtworksResults<Artwork>>
}