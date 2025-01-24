package com.sendme.directnotesui

import androidx.lifecycle.SavedStateHandle
import com.pingpad.coreui.arch.StatefulEventHandler
import com.sendme.directnotesui.DirectNotesContract.DirectNotesEvent
import com.sendme.directnotesui.DirectNotesContract.DirectNotesOneTimeEvent
import com.sendme.directnotesui.DirectNotesContract.DirectNotesState
import com.sendme.directnotesui.DirectNotesContract.MutableDirectNotesState
import com.sendme.directnotsdomain.SendMeNote
import com.sendme.directnotsdomain.usecase.AddNoteUseCase
import com.sendme.directnotsdomain.usecase.GetNotesUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class DirectNotesStatefulEventHandler @Inject constructor(
    private val getNotesUseCase: GetNotesUseCase,
    private val addNoteUseCase: AddNoteUseCase
) : StatefulEventHandler<DirectNotesEvent, DirectNotesOneTimeEvent, DirectNotesState, MutableDirectNotesState>(
    DirectNotesState()
) {

    override suspend fun process(event: DirectNotesEvent, args: Any?) {
        when (event) {
            is DirectNotesEvent.InitData -> onInitDataEvent(
                folderId = event.folderId,
                folderName = event.folderName
            )

            is DirectNotesEvent.AddNote -> onAddNoteEvent(
                folderId = stateValue.folderId,
                content = event.note
            )
        }


    }

    private suspend fun onInitDataEvent(folderName: String?, folderId: Long?) {
        if (folderId == null || folderName.isNullOrEmpty()) {
            updateUiState {
                error = com.sendme.coreui.R.string.validation_error
            }
        } else {
            updateUiState {
                this.folderId = folderId
                this.folderName = folderName
            }
            getNotesUseCase(folderId).onEach {
                updateUiState { notes = it }
            }.catch {
                updateUiState {
                    error
                }
            }.collect()
        }

    }

    private suspend fun onAddNoteEvent(folderId: Long?, content: String) {
        folderId?.let {
            val newNote = SendMeNote(
                id = System.currentTimeMillis(), content = content
            )
            addNoteUseCase(it, newNote)
        }

    }
}