package com.chatnote.coreui.arch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

abstract class EventDrivenViewModel<
        S : ConvertibleState<S, M>,
        M : MutableConvertibleState<S>,
        E : UiEvent,
        O : UiOneTimeEvent,
        H : StatefulEventHandler<E, O, S, M>
        >(
    private val statefulEventHandler: H,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {


    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onGeneralError(throwable)
    }

    val state: StateFlow<S> = statefulEventHandler.state
        .onStart {
            onStateReady()
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, statefulEventHandler.stateValue)

    val oneTimeEvent: Flow<O> = statefulEventHandler.uiEvent

    fun E.processWithLaunch() {
        viewModelScope.launch(ioDispatcher) {
            process()
        }
    }

    suspend fun E.process() {
        processEvent(this)
    }

    private suspend fun processEvent(event: E) {
        statefulEventHandler.process(event)
    }

    abstract fun onStateReady()

    abstract fun onGeneralError(throwable: Throwable)

}
