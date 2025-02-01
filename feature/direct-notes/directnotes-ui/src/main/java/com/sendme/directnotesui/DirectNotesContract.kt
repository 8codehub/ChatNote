package com.sendme.directnotesui

import androidx.annotation.StringRes
import com.pingpad.coreui.arch.ConvertibleState
import com.pingpad.coreui.arch.MutableConvertibleState
import com.pingpad.coreui.arch.UiEvent
import com.pingpad.coreui.arch.UiOneTimeEvent
import com.sendme.directnotesui.model.UiNote
import com.sendme.navigation.NavigationRoute

object DirectNotesContract {

    // Immutable State
    data class DirectNotesState(
        val folderId: Long? = null,
        val folderName: String = "",
        val folderIconUri: String? = null,
        val notes: List<UiNote> = emptyList(),
        val emptyNotes: Boolean? = null,
        override val isLoading: Boolean = false,
        @StringRes override val generalError: Int = com.sendme.coreui.R.string.general_error
    ) : ConvertibleState<DirectNotesState, MutableDirectNotesState> {
        override fun toMutable(): MutableDirectNotesState {
            return MutableDirectNotesState(
                folderId = folderId,
                folderName = folderName,
                folderIconUri = folderIconUri,
                notes = notes,
                isLoading = isLoading,
                generalError = generalError,
                emptyNotes = emptyNotes
            )
        }
    }

    // Mutable State
    class MutableDirectNotesState(
        var folderId: Long? = null,
        var folderName: String = "",
        var folderIconUri: String? = null,
        var notes: List<UiNote> = emptyList(),
        var emptyNotes: Boolean? = null,
        override var isLoading: Boolean = false,
        @StringRes override var generalError: Int = com.sendme.coreui.R.string.general_error
    ) : MutableConvertibleState<DirectNotesState> {
        override fun toImmutable(): DirectNotesState {
            return DirectNotesState(
                folderId = folderId,
                folderName = folderName,
                folderIconUri = folderIconUri,
                notes = notes,
                isLoading = isLoading,
                generalError = generalError,
                emptyNotes = emptyNotes
            )
        }
    }

    // UI Events
    sealed class DirectNotesEvent : UiEvent {
        data class LoadFolderBasicInfo(val folderId: Long) : DirectNotesEvent()
        data class LoadAllNotes(val folderId: Long) : DirectNotesEvent()
        data class AddNote(val note: String) : DirectNotesEvent()
        data class GeneralError(val throwable: Throwable) : DirectNotesEvent()
    }

    // One-Time Events
    sealed class DirectNotesOneTimeEvent : UiOneTimeEvent {
        data class FailedOperation(@StringRes val error: Int) : DirectNotesOneTimeEvent()
        data object NavigateBack : DirectNotesOneTimeEvent()
        data class NavigateTo(val route: NavigationRoute) : DirectNotesOneTimeEvent()
    }
}
