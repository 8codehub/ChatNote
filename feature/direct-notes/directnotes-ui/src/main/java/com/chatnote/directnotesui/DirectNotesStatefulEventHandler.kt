package com.chatnote.directnotesui

import chatnote.directnotesui.R
import com.chatnote.common.analytics.AnalyticsTracker
import com.chatnote.coredomain.mapper.Mapper
import com.chatnote.coredomain.models.FolderBaseInfo
import com.chatnote.coreui.arch.StatefulEventHandler
import com.chatnote.coreui.model.SystemActionType
import com.chatnote.coreui.systemactions.SystemActionTypeHandler
import com.chatnote.directnotesdomain.model.Note
import com.chatnote.directnotesdomain.model.NoteActionableContent
import com.chatnote.directnotesdomain.usecase.AddNoteUseCase
import com.chatnote.directnotesdomain.usecase.DeleteNoteUseCase
import com.chatnote.directnotesdomain.usecase.ExtractActionableContentUseCase
import com.chatnote.directnotesdomain.usecase.GetNotesUseCase
import com.chatnote.directnotesdomain.usecase.ObserveFolderUseCase
import com.chatnote.directnotesui.directnoteslist.DirectNotesContract.DirectNotesEvent
import com.chatnote.directnotesui.directnoteslist.DirectNotesContract.DirectNotesOneTimeEvent
import com.chatnote.directnotesui.directnoteslist.DirectNotesContract.DirectNotesState
import com.chatnote.directnotesui.directnoteslist.DirectNotesContract.MutableDirectNotesState
import com.chatnote.directnotesui.model.UiNote
import com.chatnote.directnotesui.model.UiNoteActionableContent
import com.chatnote.directnotesui.model.UiNoteInteraction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DirectNotesStatefulEventHandler @Inject constructor(
    private val observeNotes: GetNotesUseCase,
    private val addNoteUseCase: AddNoteUseCase,
    private val observeFolderUseCase: ObserveFolderUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val extractActionableContent: ExtractActionableContentUseCase,
    private val errorResultMapper: Mapper<Throwable, Int>,
    private val notesToUiNotes: Mapper<Note, UiNote>,
    private val actionableContentToUiNoteNoteActionableContent: Mapper<NoteActionableContent, UiNoteActionableContent>,
    private val actionTypeToSystemActionType: Mapper<UiNoteInteraction, SystemActionType>,
    private val analyticsTracker: AnalyticsTracker,
    private val systemActionTypeHandler: SystemActionTypeHandler
) : StatefulEventHandler<DirectNotesEvent, DirectNotesOneTimeEvent, DirectNotesState, MutableDirectNotesState>(
    DirectNotesState()
) {

    override suspend fun process(event: DirectNotesEvent, args: Any?) {
        when (event) {
            is DirectNotesEvent.LoadFolderBasicInfo -> onLoadFolderBasicInfoEvent(event.folderId)
            is DirectNotesEvent.LoadAllNotes -> onLoadAllNotesEvent(event.folderId)
            is DirectNotesEvent.AddNote -> onAddNoteEvent(
                folderId = stateValue.folderId, content = event.note
            )

            is DirectNotesEvent.GeneralError -> onGeneralErrorEvent(throwable = event.throwable)
            is DirectNotesEvent.NoteLongClick -> onNoteLongClickEvent(uiNote = event.note)
            is DirectNotesEvent.NoteActionClick -> onActionClickEvent(
                uiNoteInteraction = event.uiNoteInteraction
            )
        }
    }

    private suspend fun handleSystemAction(systemActionType: SystemActionType) {
        withContext(Dispatchers.Main) {
            systemActionTypeHandler.handleAction(systemActionType = systemActionType)
        }
    }

    private suspend fun onActionClickEvent(uiNoteInteraction: UiNoteInteraction) {
        when (uiNoteInteraction) {
            is UiNoteInteraction.Call,
            is UiNoteInteraction.Copy,
            is UiNoteInteraction.OpenEmail,
            is UiNoteInteraction.OpenWeb,
            is UiNoteInteraction.SMS,
            is UiNoteInteraction.Share -> handleSystemAction(
                systemActionType = actionTypeToSystemActionType.map(
                    from = uiNoteInteraction
                )
            )

            is UiNoteInteraction.Delete -> deleteNoteUseCase(
                folderId = stateValue.folderId ?: 0,
                noteId = uiNoteInteraction.noteId
            )
        }

    }

    private suspend fun onGeneralErrorEvent(throwable: Throwable) {
        analyticsTracker.trackGeneralError(
            message = throwable.message.orEmpty(), src = this.javaClass.name
        )
        DirectNotesOneTimeEvent.FailedOperation(error = errorResultMapper.map(throwable))
            .processOneTimeEvent()
    }

    private suspend fun onLoadAllNotesEvent(folderId: Long) {
        observeNotes(folderId).onEach { notes ->
            analyticsTracker.trackFolderOpen(folderId = folderId, notesCount = notes.size)
            updateUiState {
                this.notes = notesToUiNotes.mapList(from = notes)
                this.emptyNotes = notes.isEmpty()
            }
        }.catch {
            DirectNotesOneTimeEvent.FailedOperation(R.string.error_failed_to_load_notes)
                .processOneTimeEvent()
        }.collect()
    }

    private suspend fun onLoadFolderBasicInfoEvent(folderId: Long) {
        observeFolderUseCase(folderId).onEach { basicInfo ->
            if (basicInfo != null) {
                updateUiStateWithFolderInfo(basicInfo)
            } else {
                handleFolderNotFound()
            }
        }.catch {
            handleFolderNotFound()
        }.collect()
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
            addNoteUseCase(it, newNote).onSuccess {
                analyticsTracker.trackNewNote(folderId = folderId)
            }.onFailure { throwable ->
                updateUiState {
                    generalError = errorResultMapper.map(throwable)
                }
            }
        }
    }

    private suspend fun onNoteLongClickEvent(uiNote: UiNote) {
        DirectNotesOneTimeEvent.ShowActionableContentSheet(
            noteId = uiNote.id,
            actionableContentToUiNoteNoteActionableContent.map(extractActionableContent(fullMessage = uiNote.content))
        ).processOneTimeEvent()

    }
}
