package com.chatnote.directnotesui.directnoteslist

import androidx.annotation.StringRes
import com.chatnote.coreui.arch.ConvertibleState
import com.chatnote.coreui.arch.MutableConvertibleState
import com.chatnote.coreui.arch.UiEvent
import com.chatnote.coreui.arch.UiOneTimeEvent
import com.chatnote.directnotesui.model.UiNote
import com.chatnote.directnotesui.model.UiNoteActionableContent
import com.chatnote.directnotesui.model.UiNoteInteraction
import com.chatnote.navigation.NavigationRoute
import chatnote.coreui.R as CR

object DirectNotesContract {

    // Immutable State
    data class DirectNotesState(
        val folderId: Long? = null,
        val folderName: String = "",
        val folderIconUri: String? = null,
        val notes: List<UiNote> = emptyList(),
        val emptyNotes: Boolean? = null,
        override val isLoading: Boolean = false,
        @StringRes override val generalError: Int = CR.string.general_error
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
        @StringRes override var generalError: Int = chatnote.coreui.R.string.general_error
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
        data class NoteLongClick(val note: UiNote) : DirectNotesEvent()
        data class NoteActionClick(val interaction: UiNoteInteraction) : DirectNotesEvent()
        data class DeleteSelectedNote(val noteId: Long) : DirectNotesEvent()
        data class GeneralError(val throwable: Throwable) : DirectNotesEvent()
    }

    // One-Time Events
    sealed class DirectNotesOneTimeEvent : UiOneTimeEvent {
        data class FailedOperation(@StringRes val error: Int) : DirectNotesOneTimeEvent()
        data object NavigateBack : DirectNotesOneTimeEvent()
        data class NavigateTo(val route: NavigationRoute) : DirectNotesOneTimeEvent()
        data class ShowActionableContentSheet(
            val noteId: Long,
            val uiNoteActionableContent: UiNoteActionableContent
        ) : DirectNotesOneTimeEvent()

        data class AskForNoteDelete(val noteId: Long) : DirectNotesOneTimeEvent()

    }
}
