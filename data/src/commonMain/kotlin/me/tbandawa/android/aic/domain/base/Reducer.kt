package me.tbandawa.android.aic.domain.base

import me.tbandawa.android.aic.core.ArtworksResults

interface Reducer<STATE, T :Any> {
    fun reduce(result: ArtworksResults<T>, state: STATE): STATE
}