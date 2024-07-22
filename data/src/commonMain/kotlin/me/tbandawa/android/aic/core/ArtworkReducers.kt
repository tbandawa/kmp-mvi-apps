package me.tbandawa.android.aic.core

import me.tbandawa.android.aic.domain.models.Artworks
import me.tbandawa.kmm.aic.domain.models.Artwork
import kotlin.jvm.JvmName

@JvmName("reduceArtworks")
fun ArtworksResults<Artworks>.reduce(): ArtworksState<Artworks> {
    return when (this) {
        is ArtworksResults.Error -> ArtworksState.Error(data)
        is ArtworksResults.Idle -> ArtworksState.Idle
        is ArtworksResults.Loading -> ArtworksState.Loading
        is ArtworksResults.Success -> ArtworksState.Data(data)
    }
}

@JvmName("reduceArtwork")
fun ArtworksResults<Artwork>.reduce(): ArtworksState<Artwork> {
    return when (this) {
        is ArtworksResults.Error -> ArtworksState.Error(data)
        is ArtworksResults.Idle -> ArtworksState.Idle
        is ArtworksResults.Loading -> ArtworksState.Loading
        is ArtworksResults.Success -> ArtworksState.Data(data)
    }
}