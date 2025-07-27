package com.chatnote.directnotesui

import android.net.Uri
import chatnote.directnotesui.R
import com.chatnote.common.analytics.AnalyticsTracker
import com.chatnote.coredomain.mapper.Mapper
import com.chatnote.coredomain.models.FolderBaseInfo
import com.chatnote.coreui.arch.StatefulEventHandler
import com.chatnote.coreui.model.SystemActionType
import com.chatnote.coreui.systemactions.SystemActionTypeHandler
import com.chatnote.directnotesdomain.model.Note
import com.chatnote.directnotesdomain.model.NoteActionableContent
import com.chatnote.coredomain.models.NoteExtra
import com.chatnote.directnotesdomain.usecase.AddNoteUseCase
import com.chatnote.directnotesdomain.usecase.DeleteNoteUseCase
import com.chatnote.directnotesdomain.usecase.ExtractActionableContentUseCase
import com.chatnote.directnotesdomain.usecase.GetNotesUseCase
import com.chatnote.directnotesdomain.usecase.ObserveFolderUseCase
import com.chatnote.directnotesui.directnoteslist.DirectNotesContract.DirectNotesEvent
import com.chatnote.directnotesui.directnoteslist.DirectNotesContract.DirectNotesOneTimeEvent
import com.chatnote.directnotesui.directnoteslist.DirectNotesContract.DirectNotesState
import com.chatnote.directnotesui.directnoteslist.DirectNotesContract.MutableDirectNotesState
import com.chatnote.directnotesui.model.UiEditorInputAction
import com.chatnote.directnotesui.model.UiNote
import com.chatnote.directnotesui.model.UiNoteActionableContent
import com.chatnote.directnotesui.model.UiNoteExtra
import com.chatnote.directnotesui.model.UiNoteInteraction
import com.chatnote.directnotesui.processing.ExtraProcessor
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
    private val extraProcessor: ExtraProcessor,
    private val extractActionableContent: ExtractActionableContentUseCase,
    private val errorResultMapper: Mapper<Throwable, Int>,
    private val notesToUiNotes: Mapper<Note, UiNote>,
    private val uriToUiNoteExtraMapper: Mapper<Uri, UiNoteExtra>,
    private val uiNoteExtraToNoteExtra: Mapper<UiNoteExtra, NoteExtra>,
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
            is DirectNotesEvent.GeneralError -> onGeneralErrorEvent(throwable = event.throwable)
            is DirectNotesEvent.NoteLongClick -> onNoteLongClickEvent(uiNote = event.note)
            is DirectNotesEvent.NoteActionClick -> onActionClickEvent(interaction = event.interaction)
            is DirectNotesEvent.ImageSelected -> onImageSelectedEvent(uris = event.uris)
            is DirectNotesEvent.DeleteSelectedNote -> deleteNoteUseCase(noteId = event.noteId)
            is DirectNotesEvent.EditorInputActionClick -> onEditorInputActionClickEvent(interaction = event.interaction)
        }
    }

    private suspend fun onEditorInputActionClickEvent(interaction: UiEditorInputAction) {

        when (interaction) {
            UiEditorInputAction.ChooseImage -> {
                DirectNotesOneTimeEvent.OpenImageChooser.processOneTimeEvent()
            }

            is UiEditorInputAction.SaveNewNote -> onSaveNewNote(
                folderId = stateValue.folderId, content = interaction.content
            )

            is UiEditorInputAction.RemoveExtra -> {
                val updatedExtraList =
                    stateValue.noteExtrasState.extras.toMutableList()
                updatedExtraList.remove(element = interaction.uiNoteExtra)
                updateUiState {
                    noteExtrasState = stateValue.noteExtrasState.copy(extras = updatedExtraList)
                }
            }
        }

    }

    private fun onImageSelectedEvent(uris: List<Uri>) {
        updateUiState {
            noteExtrasState =
                stateValue.noteExtrasState.copy(extras = uriToUiNoteExtraMapper.mapList(uris))
        }
    }

    private suspend fun handleSystemAction(systemActionType: SystemActionType) {
        withContext(Dispatchers.Main) {
            systemActionTypeHandler.handleAction(systemActionType = systemActionType)
        }
    }

    private suspend fun onActionClickEvent(interaction: UiNoteInteraction) {
        analyticsTracker.trackNoteDetailActionDone(interaction = interaction.name)
        when (interaction) {
            is UiNoteInteraction.Call,
            is UiNoteInteraction.Copy,
            is UiNoteInteraction.OpenEmail,
            is UiNoteInteraction.OpenWeb,
            is UiNoteInteraction.SMS,
            is UiNoteInteraction.Share -> handleSystemAction(
                systemActionType = actionTypeToSystemActionType.map(
                    from = interaction
                )
            )

            is UiNoteInteraction.Delete -> {
                DirectNotesOneTimeEvent.DeleteNote(noteId = interaction.noteId)
                    .processOneTimeEvent()
            }

            is UiNoteInteraction.Edit -> {
                DirectNotesOneTimeEvent.EditNote(noteId = interaction.noteId)
                    .processOneTimeEvent()
            }

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

    private suspend fun onSaveNewNote(folderId: Long?, content: String) {
        folderId?.let {
            val localImages =
                extraProcessor.copyToLocalStorage(items = stateValue.noteExtrasState.extras)
            val newNote = Note(
                id = System.currentTimeMillis(),
                content = content,
                folderId = folderId,
                createdAt = System.currentTimeMillis(),
                extras = uiNoteExtraToNoteExtra.mapList(from = localImages)
            )
            addNoteUseCase(it, newNote).onSuccess {
                analyticsTracker.trackNewNote(folderId = folderId)
                updateUiState { noteExtrasState = noteExtrasState.copy(extras = emptyList()) }
            }.onFailure { throwable ->
                updateUiState {
                    generalError = errorResultMapper.map(throwable)
                }
            }
        }
    }

    private suspend fun onNoteLongClickEvent(uiNote: UiNote) {
        analyticsTracker.trackNoteLongClick()
        DirectNotesOneTimeEvent.ShowActionableContentSheet(
            noteId = uiNote.id,
            actionableContentToUiNoteNoteActionableContent.map(extractActionableContent(fullMessage = uiNote.content))
        ).processOneTimeEvent()

    }
}
