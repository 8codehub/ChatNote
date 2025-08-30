package com.chatnote.directnotesui.editnote

import com.chatnote.common.analytics.AnalyticsTracker
import com.chatnote.coredomain.mapper.Mapper
import com.chatnote.coreui.arch.StatefulEventHandler
import com.chatnote.directnotesdomain.usecase.GetNoteByIdUseCase
import com.chatnote.directnotesdomain.usecase.UpdateNoteContentUseCase
import com.chatnote.directnotesdomain.validator.EditNoteContentValidator
import com.chatnote.directnotesui.editnote.EditNoteContract.EditNoteEvent
import com.chatnote.directnotesui.editnote.EditNoteContract.EditNoteOneTimeEvent
import com.chatnote.directnotesui.editnote.EditNoteContract.EditNoteOneTimeEvent.FailedOperation
import com.chatnote.directnotesui.editnote.EditNoteContract.EditNoteOneTimeEvent.NoteUpdated
import com.chatnote.directnotesui.editnote.EditNoteContract.EditNoteState
import com.chatnote.directnotesui.editnote.EditNoteContract.MutableEditNoteState
import javax.inject.Inject

class EditNoteStatefulEventHandler @Inject constructor(
    private val getNoteById: GetNoteByIdUseCase,
    private val updateNoteContent: UpdateNoteContentUseCase,
    private val editNoteContentValidator: EditNoteContentValidator,
    private val mapperResultErrorToErrorId: Mapper<Throwable?, Int>,
    private val analyticsTracker: AnalyticsTracker
) : StatefulEventHandler<
        EditNoteEvent,
        EditNoteOneTimeEvent,
        EditNoteState,
        MutableEditNoteState
        >(
    EditNoteState()
) {

    override suspend fun process(event: EditNoteEvent, args: Any?) {
        when (event) {
            is EditNoteEvent.LoadNote -> onLoadNoteEvent(noteId = event.noteId)
            is EditNoteEvent.UpdateNote -> onUpdateNoteEvent(
                noteId = event.noteId,
                content = event.updatedContent
            )

            is EditNoteEvent.GeneralError -> onGeneralError(throwable = event.throwable)
            EditNoteEvent.InputTextChanged -> onInputTextChangedEvent()
        }
    }

    private suspend fun onUpdateNoteEvent(noteId: Long, content: String) {
        editNoteContentValidator(content = content).onSuccess {
            saveEditedNote(noteId = noteId, content = content)
        }.onFailure {
            updateUiState { inputError = mapperResultErrorToErrorId.map(it) }
        }
    }


    private suspend fun saveEditedNote(noteId: Long, content: String) {
        updateNoteContent(noteId = noteId, content = content).onSuccess {
            NoteUpdated.processOneTimeEvent()
        }.onFailure {
            onGeneralErrorEvent(throwable = it)
        }
    }

    private suspend fun onGeneralErrorEvent(throwable: Throwable) {
        analyticsTracker.trackGeneralError(
            message = throwable.message.orEmpty(),
            src = this.javaClass.name
        )
        FailedOperation(mapperResultErrorToErrorId.map(throwable))
            .processOneTimeEvent()
    }

    private fun onInputTextChangedEvent() {
        updateUiState { inputError = null }
    }

    private suspend fun onLoadNoteEvent(noteId: Long) {
        updateUiState { isLoading = true }
        getNoteById(noteId = noteId).onSuccess {
            updateUiState {
                isLoading = false
                note = it
            }
        }.onFailure {
            updateUiState {
                isLoading = false
                inputError = mapperResultErrorToErrorId.map(from = it)
            }
        }
    }


    private suspend fun onGeneralError(throwable: Throwable) {
        analyticsTracker.trackGeneralError(
            message = throwable.message.orEmpty(),
            src = this.javaClass.name
        )
        FailedOperation(error = mapperResultErrorToErrorId.map(throwable))
            .processOneTimeEvent()
    }

}