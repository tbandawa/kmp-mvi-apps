package me.tbandawa.android.aic.lifecycle

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import kotlinx.coroutines.launch
import me.tbandawa.android.aic.repo.AicRepository

class ArtworksViewModel(
    private val repository: AicRepository
): BaseViewModel<ArtworksState<Any>, ArtworksIntent, ArtworksEffect>() {

    override fun createInitialState(): ArtworksState<Any> = ArtworksState.Idle

    val pagedArtworks = repository
        .getArtWorks()
        .cachedIn(viewModelScope)

    override fun handleIntent(intent: ArtworksIntent) {
        when (intent) {
            is ArtworksIntent.GetArtworks -> {
                repository.getArtWorks()
            }
            is ArtworksIntent.GetArtwork -> {
                viewModelScope.launch {
                    getArtWork(id = intent.id)
                }
            }
            is ArtworksIntent.Refresh -> {}
            is ArtworksIntent.Retry -> {}
            is ArtworksIntent.Error -> {}
        }
    }

    private suspend fun getArtWork(id: Int) {
        repository.getArtwork(id = id).collect { state ->
            _state.value = state
        }
    }
}