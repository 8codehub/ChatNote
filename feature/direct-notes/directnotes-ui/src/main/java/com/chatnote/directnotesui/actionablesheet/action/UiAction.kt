package com.chatnote.directnotesui.actionablesheet.action

import com.chatnote.directnotesui.model.UiNoteInteraction

data class UiAction(
    val content: String,
    val action: UiNoteInteraction
)