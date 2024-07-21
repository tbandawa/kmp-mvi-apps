package me.tbandawa.android.aic.core

import me.tbandawa.kmm.aic.domain.base.Intent

sealed class ArtworksIntent : Intent {
    data class GetArtworks(val page: Int): ArtworksIntent()
    data class GetArtwork(val id: Int): ArtworksIntent()
    data class Error(val message: String): ArtworksIntent()
}