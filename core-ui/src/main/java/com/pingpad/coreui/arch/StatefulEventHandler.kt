package com.pingpad.coreui.arch

import androidx.compose.runtime.MutableState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class StatefulEventHandler<E, S, M>(
    initialState: S
) where S : ConvertibleState<S, M>, M : MutableConvertibleState<S> {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<S> = _state.asStateFlow()

    abstract suspend fun process(event: E, args: Any? = null)

    // Generic state update function
    protected fun updateUiState(update: M.() -> Unit) {
        _state.update { currentState ->
            currentState.toMutable().apply(update).toImmutable()
        }
    }
}