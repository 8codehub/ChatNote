package com.chatnote.directnotesui.model

data class UiActionableContent(
    val fullContent: String,
    val actionableItems: List<UiActionableItem>
)

sealed class UiActionType(val order: Int) {
    data object Call : UiActionType(1000)
    data object SMS : UiActionType(2000)
    data object Share : UiActionType(3000)
    data object OpenWeb : UiActionType(4000)
    data object OpenEmail : UiActionType(5000)
    data object Copy : UiActionType(6000)


}


data class UiActionableItem(
    val content: String,
    val actions: List<UiActionType>
)