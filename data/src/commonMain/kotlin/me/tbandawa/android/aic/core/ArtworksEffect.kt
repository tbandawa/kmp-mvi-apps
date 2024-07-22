package me.tbandawa.android.aic.core

import me.tbandawa.kmm.aic.domain.base.Effect

sealed class ArtworksEffect : Effect {
    data class Error(val error: String): ArtworksEffect()
}