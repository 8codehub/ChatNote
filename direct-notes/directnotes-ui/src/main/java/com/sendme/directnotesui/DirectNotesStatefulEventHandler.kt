package com.sendme.directnotesui

import com.pingpad.coredomain.navigation.models.FolderBaseInfo
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
    private val getNotesUseCase: GetNotesUseCase,
    private val addNoteUseCase: AddNoteUseCase,
    private val observeFolderUseCase: ObserveFolderUseCase
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

        getNotesUseCase(folderId).onEach {
            updateUiState { notes = it }
        }.catch {
            updateUiState {
                error
            }
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

        }.collect()
    }


    private fun handleBasicInfoNotFound() {

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
            addNoteUseCase(it, newNote)
        }

    }
}