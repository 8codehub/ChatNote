package com.chatnote.directnotesdomain.model

data class ActionableContent(
    val fullContent: String,
    val actionableItems: List<ActionableItem>
)

sealed class ActionType {
    data object Call : ActionType()
    data object Copy : ActionType()
    data object SMS : ActionType()
    data object Share : ActionType()
    data object OpenWeb : ActionType()
    data object OpenEmail : ActionType()
}

data class ActionableItem(
    val content: String,
    val actions: List<ActionType>
)