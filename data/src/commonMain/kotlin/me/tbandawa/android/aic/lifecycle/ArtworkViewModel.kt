package me.tbandawa.android.aic.lifecycle

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import me.tbandawa.android.aic.remote.repo.AicRepository
import me.tbandawa.android.aic.remote.responses.ArtworkResponse

class ArtworkViewModel(
    private val repository: AicRepository
): BaseViewModel<ArtworksState<ArtworkResponse>, ArtworksIntent, ArtworksEffect>() {

    override fun createInitialState(): ArtworksState<ArtworkResponse> = ArtworksState.Idle

    override fun handleIntent(intent: ArtworksIntent) {
        when (intent) {
            is ArtworksIntent.GetArtwork -> {
                viewModelScope.launch {
                    getArtWork(id = intent.id)
                }
            }
            is ArtworksIntent.Error -> {}
            else -> {}
        }
    }

    private suspend fun getArtWork(id: Int) {
        repository.getArtwork(id = id).collect { state ->
            _state.value = state
        }
    }

    override fun observeResource(provideResourceState: (ArtworksState<ArtworkResponse>) -> Unit) {
        _state.onEach {
            provideResourceState.invoke(it)
        }.launchIn(viewModelScope)
    }
}