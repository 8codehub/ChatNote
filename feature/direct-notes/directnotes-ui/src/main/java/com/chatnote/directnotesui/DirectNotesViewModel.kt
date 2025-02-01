package com.chatnote.directnotesui

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.chatnote.common.di.IoDispatcher
import com.chatnote.coreui.arch.EventDrivenViewModel
import com.chatnote.coreui.arch.StatefulEventHandler
import com.chatnote.directnotesui.DirectNotesContract.DirectNotesEvent
import com.chatnote.directnotesui.DirectNotesContract.DirectNotesOneTimeEvent
import com.chatnote.directnotesui.DirectNotesContract.MutableDirectNotesState
import com.chatnote.navigation.NavigationRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@HiltViewModel
class DirectNotesViewModel @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    savedStateHandle: SavedStateHandle,
    statefulEventHandler: StatefulEventHandler<
            DirectNotesEvent,
            DirectNotesOneTimeEvent,
            DirectNotesContract.DirectNotesState,
            MutableDirectNotesState
            >
) : EventDrivenViewModel<
        DirectNotesContract.DirectNotesState,
        MutableDirectNotesState,
        DirectNotesEvent,
        DirectNotesOneTimeEvent,
        StatefulEventHandler<
                DirectNotesEvent,
                DirectNotesOneTimeEvent,
                DirectNotesContract.DirectNotesState,
                MutableDirectNotesState
                >
        >(statefulEventHandler = statefulEventHandler, ioDispatcher = dispatcher) {

    private val args = savedStateHandle.toRoute<NavigationRoute.DirectNotes>()

    private fun loadFolderData(folderId: Long) {
        DirectNotesEvent.LoadFolderBasicInfo(folderId).processWithLaunch()
        DirectNotesEvent.LoadAllNotes(folderId).processWithLaunch()
    }

    fun addNote(note: String) {
        DirectNotesEvent.AddNote(note).processWithLaunch()
    }

    override fun onStateReady() {
        loadFolderData(args.folderId)
    }

    override fun onGeneralError(throwable: Throwable) {
        DirectNotesEvent.GeneralError(throwable = throwable).processWithLaunch()
    }
}
