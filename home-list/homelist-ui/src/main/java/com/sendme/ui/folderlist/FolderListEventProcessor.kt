package com.sendme.ui.folderlist

import com.pingpad.coredomain.mapper.Mapper
import com.pingpad.coreui.arch.StatefulEventHandler
import com.sendme.domain.model.Folder
import com.sendme.domain.usecase.DeleteFolderUseCase
import com.sendme.domain.usecase.GetFoldersUseCase
import com.sendme.domain.usecase.PinFolderUseCase
import com.sendme.domain.usecase.UnpinFolderUseCase
import com.sendme.homelistui.R
import com.sendme.ui.folderlist.FolderListContract.FolderListEvent
import com.sendme.ui.folderlist.FolderListContract.FolderListOneTimeEvent
import com.sendme.ui.folderlist.FolderListContract.FolderListState
import com.sendme.ui.folderlist.FolderListContract.MutableFolderListState
import com.sendme.ui.mapping.FolderDomainToUiFolderMapper
import com.sendme.ui.model.UiFolder
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject


class FolderListStatefulEventHandler @Inject constructor(
    private val getFolders: GetFoldersUseCase,
    private val pinFolder: PinFolderUseCase,
    private val unpinFolder: UnpinFolderUseCase,
    private val deleteFolder: DeleteFolderUseCase,
    private val folderToUiFolder: Mapper<Folder, UiFolder>
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
        deleteFolder(folderId = folderId).onSuccess { messagesCount ->
            FolderListOneTimeEvent.FolderDeleted(messagesCount = messagesCount)
                .processOneTimeEvent()
        }
    }

    private suspend fun onPinFolderEvent(folderId: Long) {
        pinFolder(folderId = folderId).onFailure {
            FolderListOneTimeEvent.FailedOperation(error = R.string.error_folder_pin)
        }
    }

    private suspend fun onUnpinFolderEvent(folderId: Long) {
        unpinFolder(folderId = folderId).onFailure {
            FolderListOneTimeEvent.FailedOperation(error = R.string.error_folder_unpin)
        }
    }

    private suspend fun onLoadFoldersEvent() {
        getFolders().onStart {
            updateUiState {
                isLoading = true
            }
        }.onEach { folders ->
            updateUiState {
                isLoading = false
                this.folders = folderToUiFolder.mapList(folders)
                foldersCount = folders.size
            }
        }.catch { _ ->
            FolderListOneTimeEvent.FailedOperation(error = R.string.error_failed_to_load_folders)
            updateUiState {
                isLoading = false
                foldersCount = null
            }
        }.collect()
    }

}