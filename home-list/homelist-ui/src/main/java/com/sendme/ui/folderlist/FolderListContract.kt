package com.sendme.ui.folderlist

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
        val foldersCount: Int? = null,
        val errorMessage: String? = null
    ) : ConvertibleState<FolderListState, MutableFolderListState> {

        override fun toMutable(): MutableFolderListState {
            return MutableFolderListState(
                isLoading = isLoading,
                folders = folders,
                errorMessage = errorMessage,
                foldersCount = foldersCount,
            )
        }
    }

    // Mutable state
    class MutableFolderListState(
        var isLoading: Boolean = false,
        var folders: List<Folder> = emptyList(),
        var errorMessage: String? = null,
        var foldersCount: Int? = null
    ) : MutableConvertibleState<FolderListState> {

        override fun toImmutable(): FolderListState {
            return FolderListState(
                isLoading = isLoading,
                folders = folders,
                errorMessage = errorMessage,
                foldersCount = foldersCount
            )
        }
    }

    // UI Events
    sealed class FolderListEvent : UiEvent {
        data object LoadFolders : FolderListEvent()
        data class PinFolder(val folderId: Long) : FolderListEvent()
        data class UnpinFolder(val folderId: Long) : FolderListEvent()
        data class DeleteFolder(val folderId: Long) : FolderListEvent()
    }

    // One-Time Events
    sealed class FolderListOneTimeEvent : UiOneTimeEvent {
        data class ShowToast(val message: String) : FolderListOneTimeEvent()
    }
}
