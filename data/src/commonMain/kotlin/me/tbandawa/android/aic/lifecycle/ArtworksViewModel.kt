package me.tbandawa.android.aic.lifecycle

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import kotlinx.coroutines.launch
import me.tbandawa.android.aic.repo.AicRepository

class ArtworksViewModel(
    private val repository: AicRepository
): BaseViewModel<ArtworksState<Any>, ArtworksAction, ArtworksEffect>() {

    override fun createInitialState(): ArtworksState<Any> = ArtworksState.Idle

    val pagedArtworks = repository
        .getArtWorks()
        .cachedIn(viewModelScope)

    init {
        handleAction(ArtworksAction.GetArtworks)
    }

    override fun handleAction(action: ArtworksAction) {
        when (action) {
            is ArtworksAction.GetArtworks -> {
                repository.getArtWorks()
            }
            is ArtworksAction.GetArtwork -> {
                viewModelScope.launch {
                    getArtWork(id = action.id)
                }
            }
            is ArtworksAction.Refresh -> {}
            is ArtworksAction.Retry -> {}
            is ArtworksAction.Error -> {}
        }
    }

    private suspend fun getArtWork(id: Int) {
        repository.getArtwork(id = id).collect { state ->
            _state.value = state
        }
    }
}