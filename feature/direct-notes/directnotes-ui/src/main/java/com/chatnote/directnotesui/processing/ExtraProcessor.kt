package com.chatnote.directnotesui.processing

import com.chatnote.directnotesui.model.UiNoteExtra

interface ExtraProcessor {

    suspend fun copyToLocalStorage(items: List<UiNoteExtra>): List<UiNoteExtra>
}