package com.sendme.ui.folderlist

import com.pingpad.coreui.arch.StatefulEventHandler
import com.sendme.domain.usecase.GetFoldersUseCase
import javax.inject.Inject


class FolderListStatefulEventHandler @Inject constructor(
    private val getFoldersUseCase: GetFoldersUseCase
) : StatefulEventHandler<FolderListContract.FolderListEvent, FolderListContract.FolderListState, FolderListContract.MutableFolderListState>(
    FolderListContract.FolderListState()
) {

    override suspend fun process(event: FolderListContract.FolderListEvent, args: Any?) {
        when (event) {
            is FolderListContract.FolderListEvent.LoadFolders -> {
                fetchFolders()
            }

            is FolderListContract.FolderListEvent.DeleteFolder -> {

            }
        }
    }


    // Fetch folders from the use case and update the state
    private suspend fun fetchFolders() {
        updateUiState {
            isLoading = true
        }
        try {
            getFoldersUseCase().collect {
                updateUiState {
                    isLoading = false
                    folders = it
                }

            }
        } catch (e: Exception) {
            updateUiState {
                isLoading = false
                errorMessage = e.message
            }
        }
    }

}