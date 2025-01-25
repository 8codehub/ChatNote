package com.pingpad.coreui.arch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<
        S : ConvertibleState<S, M>,
        M : MutableConvertibleState<S>,
        E : UiEvent,
        O : UiOneTimeEvent,
        H : StatefulEventHandler<E, O, S, M>
        >(
    private val statefulEventHandler: H
) : ViewModel() {

    val state: StateFlow<S> = statefulEventHandler.state
    val oneTimeEvent: Flow<O> = statefulEventHandler.uiEvent

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
