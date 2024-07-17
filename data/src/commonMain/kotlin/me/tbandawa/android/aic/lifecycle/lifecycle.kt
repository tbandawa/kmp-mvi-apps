package me.tbandawa.android.aic.lifecycle

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import me.tbandawa.android.aic.remote.responses.ArtworkResponse
import me.tbandawa.android.aic.remote.responses.ArtworksResponse
import me.tbandawa.android.aic.remote.responses.ErrorResponse
import kotlin.jvm.JvmName

interface State
interface Intent
interface Effect

interface Reducer<STATE, T :Any> {
    fun reduce(result: ArtworksResults<T>, state: STATE): STATE
}

sealed class ArtworksResults<out M> {
    data class Success<out M>(val data: M): ArtworksResults<M>()
    data class Error<out M>(val data: ErrorResponse? = null): ArtworksResults<M>()
    data object Loading: ArtworksResults<Nothing>()
    data object Idle: ArtworksResults<Nothing>()
}

sealed class ArtworksIntent : Intent {
    data class GetArtworks(val page: Int): ArtworksIntent()
    data class GetArtwork(val id: Int): ArtworksIntent()
    data class Error(val message: String): ArtworksIntent()
}

sealed class ArtworksEffect : Effect {
    data class Error(val error: String): ArtworksEffect()
}

sealed class ArtworksState<out M>: State {
    data class Data<out M>(val data: M): ArtworksState<M>()
    data class Error<out M>(val data: ErrorResponse? = null): ArtworksState<M>()
    data object Loading: ArtworksState<Nothing>()
    data object Idle: ArtworksState<Nothing>()
}

@JvmName("reduceArtworks")
fun ArtworksResults<ArtworksResponse>.reduce(): ArtworksState<ArtworksResponse> {
    return when (this) {
        is ArtworksResults.Error -> ArtworksState.Error(data)
        is ArtworksResults.Idle -> ArtworksState.Idle
        is ArtworksResults.Loading -> ArtworksState.Loading
        is ArtworksResults.Success -> ArtworksState.Data(data)
    }
}

@JvmName("reduceArtwork")
fun ArtworksResults<ArtworkResponse>.reduce(): ArtworksState<ArtworkResponse> {
    return when (this) {
        is ArtworksResults.Error -> ArtworksState.Error(data)
        is ArtworksResults.Idle -> ArtworksState.Idle
        is ArtworksResults.Loading -> ArtworksState.Loading
        is ArtworksResults.Success -> ArtworksState.Data(data)
    }
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