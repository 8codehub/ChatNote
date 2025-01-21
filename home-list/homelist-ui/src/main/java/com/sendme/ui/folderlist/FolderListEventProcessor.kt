package com.sendme.ui.folderlist

import com.pingpad.coreui.arch.StatefulEventHandler
import com.sendme.domain.usecase.GetFoldersUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject


class FolderListStatefulEventHandler @Inject constructor(
    private val getFoldersUseCase: GetFoldersUseCase
) : StatefulEventHandler<
        FolderListContract.FolderListEvent,
        FolderListContract.FolderListState,
        FolderListContract.MutableFolderListState
        >(
    FolderListContract.FolderListState()
) {

    override suspend fun process(event: FolderListContract.FolderListEvent, args: Any?) {
        when (event) {
            is FolderListContract.FolderListEvent.LoadFolders -> {
                fetchFolders()
            }
            is FolderListContract.FolderListEvent.DeleteFolder -> {}
        }
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