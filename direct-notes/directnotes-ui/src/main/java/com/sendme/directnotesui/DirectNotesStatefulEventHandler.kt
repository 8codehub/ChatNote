package com.sendme.directnotesui

import com.pingpad.coredomain.mapper.Mapper
import com.pingpad.coredomain.models.FolderBaseInfo
import com.pingpad.coredomain.utils.ResultError
import com.pingpad.coreui.arch.StatefulEventHandler
import com.sendme.directnotesui.DirectNotesContract.DirectNotesEvent
import com.sendme.directnotesui.DirectNotesContract.DirectNotesOneTimeEvent
import com.sendme.directnotesui.DirectNotesContract.DirectNotesState
import com.sendme.directnotesui.DirectNotesContract.MutableDirectNotesState
import com.sendme.directnotsdomain.SendMeNote
import com.sendme.directnotsdomain.usecase.AddNoteUseCase
import com.sendme.directnotsdomain.usecase.GetNotesUseCase
import com.sendme.directnotsdomain.usecase.ObserveFolderUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class DirectNotesStatefulEventHandler @Inject constructor(
    private val observeNotes: GetNotesUseCase,
    private val addNoteUseCase: AddNoteUseCase,
    private val observeFolderUseCase: ObserveFolderUseCase,
    private val errorResultMapper: Mapper<Throwable, Int>
) : StatefulEventHandler<DirectNotesEvent, DirectNotesOneTimeEvent, DirectNotesState, MutableDirectNotesState>(
    DirectNotesState()
) {

    override suspend fun process(event: DirectNotesEvent, args: Any?) {
        when (event) {
            is DirectNotesEvent.LoadFolderBasicInfo -> onLoadFolderBasicInfoEvent(
                folderId = event.folderId
            )

            is DirectNotesEvent.LoadAllNotes -> onLoadAllNotesEvent(folderId = event.folderId)

            is DirectNotesEvent.AddNote -> onAddNoteEvent(
                folderId = stateValue.folderId,
                content = event.note
            )
        }

    }

    private suspend fun onLoadAllNotesEvent(folderId: Long) {
        observeNotes(folderId).onEach {
            updateUiState {
                notes = it
                emptyNotes = it.isEmpty()
            }
        }.catch {
            DirectNotesOneTimeEvent.FailedOperation(error = R.string.error_failed_to_load_notes)
                .processOneTimeEvent()
        }.collect()
    }

    private suspend fun onLoadFolderBasicInfoEvent(folderId: Long) {
        observeFolderUseCase(folderId).onEach { basicInfo ->
            if (basicInfo != null) {
                handleBasicInfoResult(basicInfo = basicInfo)
            } else {
                handleBasicInfoNotFound()
            }
        }.catch {
            handleBasicInfoNotFound()
        }.collect()
    }


    private suspend fun handleBasicInfoNotFound() {
        DirectNotesOneTimeEvent.FailedOperation(error = R.string.error_failed_to_load_note)
            .processOneTimeEvent()
    }

    private fun handleBasicInfoResult(basicInfo: FolderBaseInfo) {
        updateUiState {
            this.folderId = basicInfo.id
            this.folderName = basicInfo.name
            this.folderIconUri = basicInfo.iconUri
        }
    }

    private suspend fun onAddNoteEvent(folderId: Long?, content: String) {
        folderId?.let {
            val newNote = SendMeNote(
                id = System.currentTimeMillis(), content = content
            )
            addNoteUseCase(it, newNote).onFailure {
                updateUiState {
                    generalError = errorResultMapper.map(from = it)
                }
            }
        }

    }
}