package com.chatnote.directnotesdomain.model

data class ActionableContent(
    val fullContent: String,
    val actionableItems: List<ActionableItem>
)

sealed class ActionType {
    data class Call(val phoneNumber: String) : ActionType()
    data class SMS(val phoneNumber: String) : ActionType()
    data class Share(val content: String) : ActionType()
    data class OpenWeb(val url: String) : ActionType()
    data class OpenEmail(val email: String) : ActionType()
    data class Copy(val content: String) : ActionType()
}

data class ActionableItem(
    val content: String,
    val actions: List<ActionType>
)