package com.sendme.ui.editor

import androidx.annotation.StringRes
import com.pingpad.coreui.arch.ConvertibleState
import com.pingpad.coreui.arch.MutableConvertibleState
import com.pingpad.coreui.arch.UiEvent
import com.pingpad.coreui.arch.UiOneTimeEvent
import com.sendme.navigation.NavigationRoute

object FolderEditorContract {

    // Immutable state
    data class FolderEditorState(
        val folderId: Long? = null,
        val folderName: String? = null,
        val folderIconUri: String? = null,
        val icons: List<String> = emptyList(),
        val isLoading: Boolean = false,
        @StringRes val inputError: Int? = null,
        @StringRes val title: Int? = null
    ) : ConvertibleState<FolderEditorState, MutableFolderEditorState> {

        override fun toMutable(): MutableFolderEditorState {
            return MutableFolderEditorState(
                folderId = folderId,
                folderName = folderName,
                folderIconUri = folderIconUri,
                icons = icons,
                isLoading = isLoading,
                inputError = inputError,
                title = title
            )
        }
    }

    // Mutable state
    class MutableFolderEditorState(
        var folderId: Long? = null,
        var folderName: String? = null,
        var folderIconUri: String? = null,
        var icons: List<String> = emptyList(),
        var isLoading: Boolean = false,
        @StringRes var inputError: Int? = null,
        @StringRes var title: Int? = null
    ) : MutableConvertibleState<FolderEditorState> {

        override fun toImmutable(): FolderEditorState {
            return FolderEditorState(
                folderId = folderId,
                folderName = folderName,
                folderIconUri = folderIconUri,
                icons = icons,
                isLoading = isLoading,
                inputError = inputError,
                title = title
            )
        }
    }

    // UI Events
    sealed class FolderEditorEvent : UiEvent {
        data class LoadFolder(val folderId: Long) : FolderEditorEvent()
        data class LoadFolderInitialState(val isEditMode: Boolean) : FolderEditorEvent()
        data class EditOrAddFolder(val name: String, val iconUri: String) :
            FolderEditorEvent()
    }

    // One-Time Events
    sealed class FolderEditorOneTimeEvent : UiOneTimeEvent {
        data class ShowToast(val message: String) : FolderEditorOneTimeEvent()
        data object NavigateBack : FolderEditorOneTimeEvent()
        data class NavigateTo(val route: NavigationRoute) : FolderEditorOneTimeEvent()
    }
}
