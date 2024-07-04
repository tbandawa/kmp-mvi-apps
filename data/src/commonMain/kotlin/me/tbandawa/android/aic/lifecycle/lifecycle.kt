package me.tbandawa.android.aic.lifecycle

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import me.tbandawa.android.aic.remote.responses.ErrorResponse

interface State
interface Intent
interface Effect

sealed class ArtworksState<out M>: State {
    data class Success<out M>(val data: M): ArtworksState<M>()
    data class Error<out M>(val data: ErrorResponse? = null): ArtworksState<M>()
    data object Loading: ArtworksState<Nothing>()
    data object Idle: ArtworksState<Nothing>()
}

sealed class ArtworksIntent : Intent {
    data object GetArtworks : ArtworksIntent()
    data class GetArtwork(val id: Int): ArtworksIntent()
    data object Refresh: ArtworksIntent()
    data object Retry: ArtworksIntent()
    data class Error(val message: String): ArtworksIntent()
}

sealed class ArtworksEffect : Effect {
    data class Error(val error: String): ArtworksEffect()
}

abstract class BaseViewModel<S: State, I: Intent, E: Effect>: ViewModel() {

    private val _effect : MutableSharedFlow<E> = MutableSharedFlow()
    val effect = _effect.asSharedFlow()

    private val initialState : S by lazy { createInitialState() }

    protected val _state : MutableStateFlow<S> = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    abstract fun createInitialState() : S

    abstract fun handleIntent(intent: I)

    @Suppress("unused")
    abstract fun observeResource(provideResourceState: (S) -> Unit)
}