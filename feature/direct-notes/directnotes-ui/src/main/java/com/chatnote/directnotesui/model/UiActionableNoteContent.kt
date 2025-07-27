package com.chatnote.directnotesui.model

import androidx.annotation.StringRes
import chatnote.directnotesui.R

data class UiNoteActionableContent(
    val fullContent: String,
    val actionableItems: List<UiActionableItem>
)

sealed class UiNoteInteraction(val order: Int, val name: String) {
    data class Edit(val noteId: Long) : UiNoteInteraction(order = 1000, name = "edit_note")
    data class Call(val phoneNumber: String) : UiNoteInteraction(order = 2000, name = "call")
    data class SMS(val phoneNumber: String) : UiNoteInteraction(order = 3000, name = "sms")
    data class Share(val content: String) : UiNoteInteraction(order = 4000, name = "share")
    data class OpenWeb(val url: String) : UiNoteInteraction(order = 5000, name = "open_web")
    data class OpenEmail(val email: String) : UiNoteInteraction(order = 6000, name = "open_email")
    data class Copy(val content: String) : UiNoteInteraction(order = 7000, name = "copy")
    data class Delete(val noteId: Long) : UiNoteInteraction(order = 8000, name = "delete")
}

@StringRes
fun UiNoteInteraction.getActionStringResId(): Int {
    return when (this) {
        is UiNoteInteraction.Edit -> R.string.action_call
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