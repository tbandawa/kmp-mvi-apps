package me.tbandawa.android.aic.domain.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

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