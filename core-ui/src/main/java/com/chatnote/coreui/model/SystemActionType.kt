package com.chatnote.coreui.model

sealed class SystemActionType {
    data object Call : SystemActionType()
    data object Copy : SystemActionType()
    data object SMS : SystemActionType()
    data object Share : SystemActionType()
    data object OpenWeb : SystemActionType()
    data object SendEmail : SystemActionType()
}