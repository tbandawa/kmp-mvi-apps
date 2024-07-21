package me.tbandawa.android.aic.core

import me.tbandawa.android.aic.remote.responses.ErrorResponse

sealed class ArtworksResults<out M> {
    data class Success<out M>(val data: M): ArtworksResults<M>()
    data class Error<out M>(val data: ErrorResponse? = null): ArtworksResults<M>()
    data object Loading: ArtworksResults<Nothing>()
    data object Idle: ArtworksResults<Nothing>()
}