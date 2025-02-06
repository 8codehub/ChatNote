package com.chatnote.directnotesui.actionablesheet.action

import com.chatnote.directnotesui.model.UiActionType

data class UiAction(
    val content: String,
    val action: UiActionType
)