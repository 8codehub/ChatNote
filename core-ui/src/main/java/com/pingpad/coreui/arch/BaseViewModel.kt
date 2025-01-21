package com.pingpad.coreui.arch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<
        S : ConvertibleState<S, M>,
        M : MutableConvertibleState<S>,
        E : UiEvent,
        O : UiOneTimeEvent,
        H : StatefulEventHandler<E, S, M>
        >(
    private val statefulEventHandler: H
) : ViewModel() {

    // State exposed to subclasses
    val state: StateFlow<S> = statefulEventHandler.state

    // One-time events
    private val _oneTimeEvent = MutableSharedFlow<O>()
    val oneTimeEvent: SharedFlow<O> = _oneTimeEvent.asSharedFlow()

    // Emit one-time events
    protected suspend fun emitOneTimeEvent(event: O) {
        _oneTimeEvent.emit(event)
    }

    fun E.processWithLaunch() {
        viewModelScope.launch {
            process()
        }
    }

    suspend fun E.process() {
        processEvent(this)
    }

    private suspend fun processEvent(event: E) {
        statefulEventHandler.process(event)
    }
}
