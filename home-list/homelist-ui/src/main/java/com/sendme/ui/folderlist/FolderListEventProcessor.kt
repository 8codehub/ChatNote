package com.sendme.ui.folderlist

import com.pingpad.coreui.arch.StatefulEventHandler
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
    private val getFoldersUseCase: GetFoldersUseCase,
    private val pinFolder: PinFolderUseCase,
    private val unpinFolder: UnpinFolderUseCase
) : StatefulEventHandler<FolderListEvent, FolderListOneTimeEvent, FolderListState, MutableFolderListState>(FolderListState()) {

    override suspend fun process(event: FolderListEvent, args: Any?) {
        when (event) {
            is FolderListEvent.LoadFolders -> {
                fetchFolders()
            }

            is FolderListEvent.PinFolder -> {
                makeFolderPinned(event.folderId)
            }

            is FolderListEvent.UnpinFolder -> {
                makeFolderUnPinned(event.folderId)
            }

            is FolderListEvent.DeleteFolder -> {}
        }
    }

    private suspend fun makeFolderPinned(folderId: Long) {
        pinFolder(folderId = folderId)
    }

    private suspend fun makeFolderUnPinned(folderId: Long) {
        unpinFolder(folderId = folderId)
    }

    private suspend fun fetchFolders() {
        getFoldersUseCase()
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