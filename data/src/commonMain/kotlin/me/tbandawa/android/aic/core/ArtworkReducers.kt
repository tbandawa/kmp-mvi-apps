package me.tbandawa.android.aic.core

import me.tbandawa.android.aic.remote.responses.ArtworkResponse
import me.tbandawa.android.aic.remote.responses.ArtworksResponse
import kotlin.jvm.JvmName

@JvmName("reduceArtworks")
fun ArtworksResults<ArtworksResponse>.reduce(): ArtworksState<ArtworksResponse> {
    return when (this) {
        is ArtworksResults.Error -> ArtworksState.Error(data)
        is ArtworksResults.Idle -> ArtworksState.Idle
        is ArtworksResults.Loading -> ArtworksState.Loading
        is ArtworksResults.Success -> ArtworksState.Data(data)
    }
}

@JvmName("reduceArtwork")
fun ArtworksResults<ArtworkResponse>.reduce(): ArtworksState<ArtworkResponse> {
    return when (this) {
        is ArtworksResults.Error -> ArtworksState.Error(data)
        is ArtworksResults.Idle -> ArtworksState.Idle
        is ArtworksResults.Loading -> ArtworksState.Loading
        is ArtworksResults.Success -> ArtworksState.Data(data)
    }
}