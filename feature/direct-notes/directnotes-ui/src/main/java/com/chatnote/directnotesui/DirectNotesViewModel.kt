package com.chatnote.directnotesui

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.chatnote.common.di.IoDispatcher
import com.chatnote.coreui.arch.EventDrivenViewModel
import com.chatnote.coreui.arch.StatefulEventHandler
import com.chatnote.directnotesui.directnoteslist.DirectNotesContract.DirectNotesEvent
import com.chatnote.directnotesui.directnoteslist.DirectNotesContract.DirectNotesOneTimeEvent
import com.chatnote.directnotesui.directnoteslist.DirectNotesContract.DirectNotesState
import com.chatnote.directnotesui.directnoteslist.DirectNotesContract.MutableDirectNotesState
import com.chatnote.directnotesui.model.UiNote
import com.chatnote.directnotesui.model.UiNoteInteraction
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
            DirectNotesState,
            MutableDirectNotesState
            >
) : EventDrivenViewModel<
        DirectNotesState,
        MutableDirectNotesState,
        DirectNotesEvent,
        DirectNotesOneTimeEvent,
        StatefulEventHandler<
                DirectNotesEvent,
                DirectNotesOneTimeEvent,
                DirectNotesState,
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

    fun onNoteLongClick(uiNote: UiNote) {
        DirectNotesEvent.NoteLongClick(note = uiNote).processWithLaunch()
    }

    fun handelAction(interaction: UiNoteInteraction) {
        DirectNotesEvent.NoteActionClick(interaction = interaction)
            .processWithLaunch()

    }

    fun deleteSelectedNote(noteId: Long) {
        DirectNotesEvent.DeleteSelectedNote(noteId = noteId).processWithLaunch()
    }
}
