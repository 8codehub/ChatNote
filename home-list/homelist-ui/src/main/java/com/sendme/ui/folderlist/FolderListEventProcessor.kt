package com.sendme.ui.folderlist

import com.pingpad.coreui.arch.StatefulEventHandler
import com.sendme.domain.usecase.DeleteFolderUseCase
import com.sendme.domain.usecase.GetFoldersUseCase
import com.sendme.domain.usecase.PinFolderUseCase
import com.sendme.domain.usecase.UnpinFolderUseCase
import com.sendme.ui.folderlist.FolderListContract.FolderListEvent
import com.sendme.ui.folderlist.FolderListContract.FolderListOneTimeEvent
import com.sendme.ui.folderlist.FolderListContract.FolderListState
import com.sendme.ui.folderlist.FolderListContract.MutableFolderListState
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject


class FolderListStatefulEventHandler @Inject constructor(
    private val getFolders: GetFoldersUseCase,
    private val pinFolder: PinFolderUseCase,
    private val unpinFolder: UnpinFolderUseCase,
    private val deleteFolder: DeleteFolderUseCase
) : StatefulEventHandler<FolderListEvent, FolderListOneTimeEvent, FolderListState, MutableFolderListState>(
    FolderListState()
) {

    override suspend fun process(event: FolderListEvent, args: Any?) {
        when (event) {
            is FolderListEvent.LoadFolders -> {
                onLoadFoldersEvent()
            }

            is FolderListEvent.PinFolder -> {
                onPinFolderEvent(event.folderId)
            }

            is FolderListEvent.UnpinFolder -> {
                onUnpinFolderEvent(event.folderId)
            }

            is FolderListEvent.DeleteFolder -> {
                onDeleteFolderEvent(folderId = event.folderId)
            }
        }
    }

    private suspend fun onDeleteFolderEvent(folderId: Long) {
        val result = deleteFolder(folderId = folderId)
        if (result.isSuccess) {
            _uiEvent.send(FolderListOneTimeEvent.ShowToast("Deleted " + result.getOrNull()))
        }
    }

    private suspend fun onPinFolderEvent(folderId: Long) {
        pinFolder(folderId = folderId)
    }

    private suspend fun onUnpinFolderEvent(folderId: Long) {
        unpinFolder(folderId = folderId)
    }

    private suspend fun onLoadFoldersEvent() {
        getFolders()
            .onEach { folders ->
                updateUiState {
                    isLoading = false
                    this.folders = folders
                    foldersCount = folders.size
                }
            }
            .catch { e ->
                updateUiState {
                    isLoading = false
                    errorMessage = e.message
                    foldersCount = null
                }
            }
            .onStart {
                updateUiState {
                    isLoading = true
                }
            }.collect()
    }

}