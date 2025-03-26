package com.chatnote.directnotesui.editnote

import androidx.annotation.StringRes
import com.chatnote.coreui.arch.ConvertibleState
import com.chatnote.coreui.arch.MutableConvertibleState
import com.chatnote.coreui.arch.UiEvent
import com.chatnote.coreui.arch.UiOneTimeEvent
import com.chatnote.directnotesdomain.model.Note
import com.chatnote.navigation.NavigationRoute
import chatnote.coreui.R as CR

object EditNoteContract {

    // Immutable state
    data class EditNoteState(
        val note: Note? = null,
        override val isLoading: Boolean = false,
        @StringRes val inputError: Int? = null,
        @StringRes val title: Int? = null,
        @StringRes override val generalError: Int = CR.string.general_error
    ) : ConvertibleState<EditNoteState, MutableEditNoteState> {

        override fun toMutable(): MutableEditNoteState {
            return MutableEditNoteState(
                note = note,
                isLoading = isLoading,
                inputError = inputError,
                title = title,
                generalError = generalError
            )
        }
    }

    // Mutable state
    class MutableEditNoteState(
        var note: Note? = null,
        override var isLoading: Boolean = false,
        @StringRes var inputError: Int? = null,
        @StringRes var title: Int? = null,
        @StringRes override var generalError: Int = CR.string.general_error
    ) : MutableConvertibleState<EditNoteState> {

        override fun toImmutable(): EditNoteState {
            return EditNoteState(
                note = note,
                isLoading = isLoading,
                inputError = inputError,
                title = title,
                generalError = generalError
            )
        }
    }

    // UI Events
    sealed class EditNoteEvent : UiEvent {
        data class LoadNote(val noteId: Long) : EditNoteEvent()
        data class UpdateNote(val noteId: Long, val updatedContent: String) : EditNoteEvent()
        data class GeneralError(val throwable: Throwable) : EditNoteEvent()

        data object InputTextChanged : EditNoteEvent()

    }

    // One-Time Events
    sealed class EditNoteOneTimeEvent : UiOneTimeEvent {
        data class ShowToast(val message: String) : EditNoteOneTimeEvent()
        data object NavigateBack : EditNoteOneTimeEvent()
        data object NoteUpdated : EditNoteOneTimeEvent()
        data class FailedOperation(@StringRes val error: Int) : EditNoteOneTimeEvent()
        data class NavigateTo(val route: NavigationRoute) : EditNoteOneTimeEvent()
    }
}
