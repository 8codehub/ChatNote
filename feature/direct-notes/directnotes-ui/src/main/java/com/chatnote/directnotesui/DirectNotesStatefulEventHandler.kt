package com.chatnote.directnotesui

import chatnote.directnotesui.R
import com.chatnote.coredomain.mapper.Mapper
import com.chatnote.coredomain.models.FolderBaseInfo
import com.chatnote.coreui.arch.StatefulEventHandler
import com.chatnote.directnotesui.DirectNotesContract.DirectNotesEvent
import com.chatnote.directnotesui.DirectNotesContract.DirectNotesOneTimeEvent
import com.chatnote.directnotesui.DirectNotesContract.DirectNotesState
import com.chatnote.directnotesui.DirectNotesContract.MutableDirectNotesState
import com.chatnote.directnotesui.model.UiNote
import com.chatnote.directnotesdomain.model.Note
import com.chatnote.directnotesdomain.usecase.AddNoteUseCase
import com.chatnote.directnotesdomain.usecase.GetNotesUseCase
import com.chatnote.directnotesdomain.usecase.ObserveFolderUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class DirectNotesStatefulEventHandler @Inject constructor(
    private val observeNotes: GetNotesUseCase,
    private val addNoteUseCase: AddNoteUseCase,
    private val observeFolderUseCase: ObserveFolderUseCase,
    private val errorResultMapper: Mapper<Throwable, Int>,
    private val notesToUiNotes: Mapper<Note, UiNote>
) : StatefulEventHandler<
        DirectNotesEvent,
        DirectNotesOneTimeEvent,
        DirectNotesState,
        MutableDirectNotesState
        >(DirectNotesState()) {

    override suspend fun process(event: DirectNotesEvent, args: Any?) {
        when (event) {
            is DirectNotesEvent.LoadFolderBasicInfo -> onLoadFolderBasicInfoEvent(event.folderId)
            is DirectNotesEvent.LoadAllNotes -> onLoadAllNotesEvent(event.folderId)
            is DirectNotesEvent.AddNote -> onAddNoteEvent(
                folderId = stateValue.folderId,
                content = event.note
            )

            is DirectNotesEvent.GeneralError -> onGeneralErrorEvent(throwable = event.throwable)
        }
    }

    private suspend fun onGeneralErrorEvent(throwable: Throwable) {
        DirectNotesOneTimeEvent.FailedOperation(error = errorResultMapper.map(throwable))
            .processOneTimeEvent()
    }

    private suspend fun onLoadAllNotesEvent(folderId: Long) {
        observeNotes(folderId)
            .onEach { notes ->
                updateUiState {
                    this.notes = notesToUiNotes.mapList(from = notes)
                    this.emptyNotes = notes.isEmpty()
                }
            }
            .catch {
                DirectNotesOneTimeEvent.FailedOperation(R.string.error_failed_to_load_notes)
                    .processOneTimeEvent()
            }
            .collect()
    }

    private suspend fun onLoadFolderBasicInfoEvent(folderId: Long) {
        observeFolderUseCase(folderId)
            .onEach { basicInfo ->
                if (basicInfo != null) {
                    updateUiStateWithFolderInfo(basicInfo)
                } else {
                    handleFolderNotFound()
                }
            }
            .catch {
                handleFolderNotFound()
            }
            .collect()
    }

    private suspend fun handleFolderNotFound() {
        DirectNotesOneTimeEvent.FailedOperation(R.string.error_failed_to_load_note)
            .processOneTimeEvent()
    }

    private fun updateUiStateWithFolderInfo(basicInfo: FolderBaseInfo) {
        updateUiState {
            folderId = basicInfo.id
            folderName = basicInfo.name
            folderIconUri = basicInfo.iconUri
        }
    }

    private suspend fun onAddNoteEvent(folderId: Long?, content: String) {
        folderId?.let {
            val newNote = Note(
                id = System.currentTimeMillis(),
                content = content,
                folderId = folderId,
                createdAt = System.currentTimeMillis()
            )
            addNoteUseCase(it, newNote).onFailure { throwable ->
                updateUiState {
                    generalError = errorResultMapper.map(throwable)
                }
            }
        }
    }
}
