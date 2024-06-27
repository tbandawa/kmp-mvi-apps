package me.tbandawa.android.aic.lifecycle

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import me.tbandawa.android.aic.remote.responses.ErrorResponse

interface State
interface Action
interface Effect

sealed class ArtworksState<out M>: State {
    data class Success<out M>(val data: M): ArtworksState<M>()
    data class Error<out M>(val data: ErrorResponse? = null): ArtworksState<M>()
    data object Loading: ArtworksState<Nothing>()
    data object Idle: ArtworksState<Nothing>()
}

sealed class ArtworksAction : Action {
    data object GetArtworks : ArtworksAction()
    data class GetArtwork(val id: Int): ArtworksAction()
    data object Refresh: ArtworksAction()
    data object Retry: ArtworksAction()
    data class Error(val message: String): ArtworksAction()
}

sealed class ArtworksEffect : Effect {
    data class Error(val error: String): ArtworksEffect()
}

abstract class BaseViewModel<S: State, A: Action, E: Effect>: ViewModel() {

    private val _effect : MutableSharedFlow<E> = MutableSharedFlow()
    val effect = _effect.asSharedFlow()

    private val initialState : S by lazy { createInitialState() }

    protected val _state : MutableStateFlow<S> = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    abstract fun createInitialState() : S

    abstract fun handleAction(action: A)
}