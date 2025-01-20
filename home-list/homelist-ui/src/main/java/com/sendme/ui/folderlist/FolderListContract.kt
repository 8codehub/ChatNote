package com.sendme.ui.folderlist

import com.pingpad.coreui.arch.BaseState
import com.pingpad.coreui.arch.ConvertibleState
import com.pingpad.coreui.arch.MutableConvertibleState
import com.pingpad.coreui.arch.UiEvent
import com.pingpad.coreui.arch.UiOneTimeEvent
import com.sendme.domain.model.Folder

object FolderListContract {

    // Immutable state
    data class FolderListState(
        val isLoading: Boolean = false,
        val folders: List<Folder> = emptyList(),
        val errorMessage: String? = null
    ) : ConvertibleState<FolderListState, MutableFolderListState> {

        override fun toMutable(): MutableFolderListState {
            return MutableFolderListState(
                isLoading = isLoading,
                folders = folders,
                errorMessage = errorMessage
            )
        }
    }

    // Mutable state
    data class MutableFolderListState(
        var isLoading: Boolean = false,
        var folders: List<Folder> = emptyList(),
        var errorMessage: String? = null
    ) : MutableConvertibleState<FolderListState> {

        override fun toImmutable(): FolderListState {
            return FolderListState(
                isLoading = isLoading,
                folders = folders,
                errorMessage = errorMessage
            )
        }
    }

    // UI Events
    sealed class FolderListEvent : UiEvent {
        data object LoadFolders : FolderListEvent()
        data class DeleteFolder(val folderId: String) : FolderListEvent()
        /*
                data class CreateFolder(val folderName: String) : FolderListEvent()
        */
    }

    // One-Time Events
    sealed class FolderListOneTimeEvent : UiOneTimeEvent {
        data class ShowToast(val message: String) : FolderListOneTimeEvent()
        data class NavigateToFolderDetails(val folderId: Long) : FolderListOneTimeEvent()
    }
}
