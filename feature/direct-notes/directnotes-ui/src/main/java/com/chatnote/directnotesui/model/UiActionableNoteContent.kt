package com.chatnote.directnotesui.model

import androidx.annotation.StringRes
import chatnote.directnotesui.R

data class UiNoteActionableContent(
    val fullContent: String,
    val actionableItems: List<UiActionableItem>
)

sealed class UiNoteInteraction(val order: Int) {
    data class Call(val phoneNumber: String) : UiNoteInteraction(1000)
    data class SMS(val phoneNumber: String) : UiNoteInteraction(2000)
    data class Share(val content: String) : UiNoteInteraction(3000)
    data class OpenWeb(val url: String) : UiNoteInteraction(4000)
    data class OpenEmail(val email: String) : UiNoteInteraction(5000)
    data class Copy(val content: String) : UiNoteInteraction(6000)
    data class Delete(val noteId: Long) : UiNoteInteraction(7000)
}

@StringRes
fun UiNoteInteraction.getActionStringResId(): Int {
    return when (this) {
        is UiNoteInteraction.Call -> R.string.action_call
        is UiNoteInteraction.SMS -> R.string.action_sms
        is UiNoteInteraction.Share -> R.string.action_share
        is UiNoteInteraction.OpenWeb -> R.string.action_open_web
        is UiNoteInteraction.OpenEmail -> R.string.action_open_email
        is UiNoteInteraction.Copy -> R.string.action_copy
        is UiNoteInteraction.Delete -> R.string.action_delete
    }
}


data class UiActionableItem(
    val content: String,
    val actions: List<UiNoteInteraction> = emptyList()
)