package me.tbandawa.android.aic.viewmodels

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import me.tbandawa.android.aic.core.ArtworksEffect
import me.tbandawa.android.aic.core.ArtworksIntent
import me.tbandawa.android.aic.core.ArtworksState
import me.tbandawa.android.aic.domain.base.BaseViewModel
import me.tbandawa.android.aic.core.reduce
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
            _state.value = state.reduce()
        }
    }

    override fun observeResource(provideResourceState: (ArtworksState<ArtworkResponse>) -> Unit) {
        _state.onEach {
            provideResourceState.invoke(it)
        }.launchIn(viewModelScope)
    }
}