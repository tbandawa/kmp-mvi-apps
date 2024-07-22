package me.tbandawa.android.aic.viewmodels

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import me.tbandawa.android.aic.core.ArtworksEffect
import me.tbandawa.android.aic.core.ArtworksIntent
import me.tbandawa.android.aic.core.ArtworksState
import me.tbandawa.android.aic.core.reduce
import me.tbandawa.android.aic.domain.base.BaseViewModel
import me.tbandawa.android.aic.domain.models.Artworks
import me.tbandawa.android.aic.domain.repository.AicRepository

class ArtworksViewModel(
    private val repository: AicRepository
): BaseViewModel<ArtworksState<Artworks>, ArtworksIntent, ArtworksEffect>() {

    override fun createInitialState(): ArtworksState<Artworks> = ArtworksState.Idle

    val pagedArtworks = repository
        .getArtWorks()
        .cachedIn(viewModelScope)

    override fun handleIntent(intent: ArtworksIntent) {
        when (intent) {
            is ArtworksIntent.GetArtworks -> {
                viewModelScope.launch {
                    getArtWorks(page = intent.page)
                }
            }
            is ArtworksIntent.Error -> {}
            else -> {}
        }
    }

    private suspend fun getArtWorks(page: Int) {
        repository.getArtworks(page = page).collect { state ->
            _state.value = state.reduce()
        }
    }

    override fun observeResource(provideResourceState: (ArtworksState<Artworks>) -> Unit) {
        _state.onEach {
            provideResourceState.invoke(it)
        }.launchIn(viewModelScope)
    }
}