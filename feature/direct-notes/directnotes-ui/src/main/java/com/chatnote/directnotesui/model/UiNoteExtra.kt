package com.chatnote.directnotesui.model


sealed class UiNoteExtra {
    data class UiNoteImageExtra(val uri: String) : UiNoteExtra()
}