package me.tbandawa.android.aic.core

import me.tbandawa.android.aic.remote.responses.ErrorResponse
import me.tbandawa.kmm.aic.domain.base.State

sealed class ArtworksState<out M>: State {
    data class Data<out M>(val data: M): ArtworksState<M>()
    data class Error<out M>(val data: ErrorResponse? = null): ArtworksState<M>()
    data object Loading: ArtworksState<Nothing>()
    data object Idle: ArtworksState<Nothing>()
}