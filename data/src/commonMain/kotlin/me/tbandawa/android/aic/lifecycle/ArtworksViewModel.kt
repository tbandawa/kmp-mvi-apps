package me.tbandawa.android.aic.lifecycle

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import me.tbandawa.android.aic.remote.repo.AicRepository

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
                viewModelScope.launch {
                    getArtWorks(page = intent.page)
                }
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

    private suspend fun getArtWorks(page: Int) {
        repository.getArtworks(page = page).collect { state ->
            _state.value = state
        }
    }

    override fun observeResource(provideResourceState: (ArtworksState<Any>) -> Unit) {
        _state.onEach {
            provideResourceState.invoke(it)
        }.launchIn(viewModelScope)
    }
}