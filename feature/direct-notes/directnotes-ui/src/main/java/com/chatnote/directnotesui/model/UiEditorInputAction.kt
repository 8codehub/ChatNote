package com.chatnote.directnotesui.model

sealed class UiEditorInputAction {
    data object ChooseImage : UiEditorInputAction()
    data class RemoveExtra(val uiNoteExtra: UiNoteExtra) : UiEditorInputAction()
    data class SaveNewNote(val content: String) : UiEditorInputAction()
}