package com.sendme.ui.folderlist

import androidx.annotation.StringRes
import com.pingpad.coreui.arch.ConvertibleState
import com.pingpad.coreui.arch.MutableConvertibleState
import com.pingpad.coreui.arch.UiEvent
import com.pingpad.coreui.arch.UiOneTimeEvent
import com.sendme.domain.model.Folder
import com.sendme.homelistui.R

object FolderListContract {

    // Immutable state
    data class FolderListState(
        override val isLoading: Boolean = false,
        val folders: List<Folder> = emptyList(),
        val foldersCount: Int? = null,
        val errorMessage: String? = null,
        @StringRes override val generalError: Int = R.string.general_error,
    ) : ConvertibleState<FolderListState, MutableFolderListState> {

        override fun toMutable(): MutableFolderListState {
            return MutableFolderListState(
                isLoading = isLoading,
                folders = folders,
                errorMessage = errorMessage,
                foldersCount = foldersCount,
                generalError = generalError
            )
        }
    }

    // Mutable state
    class MutableFolderListState(
        override var isLoading: Boolean = false,
        var folders: List<Folder> = emptyList(),
        var errorMessage: String? = null,
        var foldersCount: Int? = null,
        @StringRes override var generalError: Int = R.string.general_error,
    ) : MutableConvertibleState<FolderListState> {

        override fun toImmutable(): FolderListState {
            return FolderListState(
                isLoading = isLoading,
                folders = folders,
                errorMessage = errorMessage,
                foldersCount = foldersCount,
                generalError = generalError
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
        data class FolderDeleted(val messagesCount: Int) :
            FolderListOneTimeEvent()

        data class FailedOperation(@StringRes val error: Int) : FolderListOneTimeEvent()

    }
}
