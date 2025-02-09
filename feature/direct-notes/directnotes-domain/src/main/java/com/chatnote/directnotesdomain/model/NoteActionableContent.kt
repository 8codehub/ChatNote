package com.chatnote.directnotesdomain.model

data class NoteActionableContent(
    val fullContent: String,
    val noteActionableItems: List<NoteActionableItem>
)

sealed class NoteActionType {
    data class Call(val phoneNumber: String) : NoteActionType()
    data class SMS(val phoneNumber: String) : NoteActionType()
    data class Share(val content: String) : NoteActionType()
    data class OpenWeb(val url: String) : NoteActionType()
    data class OpenEmail(val email: String) : NoteActionType()
    data class Copy(val content: String) : NoteActionType()
}

data class NoteActionableItem(
    val content: String,
    val actions: List<NoteActionType>
)