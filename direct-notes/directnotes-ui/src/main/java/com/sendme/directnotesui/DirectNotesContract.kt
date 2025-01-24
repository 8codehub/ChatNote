package com.sendme.directnotesui

import androidx.annotation.StringRes
import com.pingpad.coreui.arch.ConvertibleState
import com.pingpad.coreui.arch.MutableConvertibleState
import com.pingpad.coreui.arch.UiEvent
import com.pingpad.coreui.arch.UiOneTimeEvent
import com.sendme.directnotsdomain.SendMeNote
import com.sendme.navigation.NavigationRoute

object DirectNotesContract {

    // Immutable state
    data class DirectNotesState(
        val folderId: Long? = null,
        val folderName: String = "",
        val folderIconUri: String? = null,
        val notes: List<SendMeNote> = emptyList(),
        val emptyNotes: Boolean? = null,
        val isLoading: Boolean = false,
        @StringRes val error: Int? = null
    ) : ConvertibleState<DirectNotesState, MutableDirectNotesState> {

        override fun toMutable(): MutableDirectNotesState {
            return MutableDirectNotesState(
                folderId = folderId,
                folderName = folderName,
                folderIconUri = folderIconUri,
                notes = notes,
                isLoading = isLoading,
                error = error,
                emptyNotes = emptyNotes
            )
        }
    }

    // Mutable state
    class MutableDirectNotesState(
        var folderId: Long? = null,
        var folderName: String = "",
        var folderIconUri: String? = null,
        var notes: List<SendMeNote> = emptyList(),
        var isLoading: Boolean = false,
        var emptyNotes: Boolean? = null,
        @StringRes var error: Int? = null,
    ) : MutableConvertibleState<DirectNotesState> {

        override fun toImmutable(): DirectNotesState {
            return DirectNotesState(
                folderId = folderId,
                folderName = folderName,
                folderIconUri = folderIconUri,
                notes = notes,
                isLoading = isLoading,
                error = error,
                emptyNotes = emptyNotes
            )
        }
    }

    // UI Events
    sealed class DirectNotesEvent : UiEvent {
        data class LoadFolderBasicInfo(
            val folderId: Long
        ) : DirectNotesEvent()

        data class LoadAllNotes(
            val folderId: Long
        ) : DirectNotesEvent()

        data class AddNote(
            val note: String
        ) : DirectNotesEvent()
    }

    // One-Time Events
    sealed class DirectNotesOneTimeEvent : UiOneTimeEvent {
        data class ShowToast(val message: String) : DirectNotesOneTimeEvent()
        data object NavigateBack : DirectNotesOneTimeEvent()
        data class NavigateTo(val route: NavigationRoute) : DirectNotesOneTimeEvent()
    }
}
