package com.chatnote.directnotesui.editnote

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.chatnote.common.di.IoDispatcher
import com.chatnote.coreui.arch.EventDrivenViewModel
import com.chatnote.coreui.arch.StatefulEventHandler
import com.chatnote.directnotesdomain.model.Note
import com.chatnote.directnotesui.editnote.EditNoteContract.EditNoteEvent
import com.chatnote.directnotesui.editnote.EditNoteContract.EditNoteOneTimeEvent
import com.chatnote.directnotesui.editnote.EditNoteContract.EditNoteState
import com.chatnote.directnotesui.editnote.EditNoteContract.MutableEditNoteState
import com.chatnote.navigation.NavigationRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@HiltViewModel
class EditNoteViewModel @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    savedStateHandle: SavedStateHandle,
    editNoteStatefulEventHandler: StatefulEventHandler<
            EditNoteEvent,
            EditNoteOneTimeEvent,
            EditNoteState,
            MutableEditNoteState
            >
) : EventDrivenViewModel<
        EditNoteState,
        MutableEditNoteState,
        EditNoteEvent,
        EditNoteOneTimeEvent,
        StatefulEventHandler<
                EditNoteEvent,
                EditNoteOneTimeEvent,
                EditNoteState,
                MutableEditNoteState
                >
        >(statefulEventHandler = editNoteStatefulEventHandler, ioDispatcher = dispatcher) {

    private var editNoteRoute = savedStateHandle.toRoute<NavigationRoute.EditNote>()

    fun update(updatedNote: Note) {
        EditNoteEvent.UpdateNote(
            noteId = updatedNote.id,
            updatedContent = updatedNote.content
        ).processWithLaunch()
    }


    override fun onStateReady() {
        EditNoteEvent.LoadNote(editNoteRoute.noteId)
            .processWithLaunch()
    }

    override fun onGeneralError(throwable: Throwable) {
        EditNoteEvent.GeneralError(throwable = throwable).processWithLaunch()
    }

    suspend fun onTextChanged() {
        EditNoteEvent.InputTextChanged.process()
    }

}
