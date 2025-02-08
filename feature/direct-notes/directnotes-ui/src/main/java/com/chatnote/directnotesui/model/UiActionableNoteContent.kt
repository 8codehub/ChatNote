package com.chatnote.directnotesui.model

data class UiActionableContent(
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
    data class Delete(val noteId: String) : UiNoteInteraction(7000)
}



data class UiActionableItem(
    val content: String,
    val actions: List<UiNoteInteraction>
)