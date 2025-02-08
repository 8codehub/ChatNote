package com.chatnote.coreui.model

sealed class SystemActionType {
    data class Call(val phoneNumber: String) : SystemActionType()
    data class SMS(val phoneNumber: String, val message: String = "") : SystemActionType()
    data class Share(val content: String) : SystemActionType()
    data class OpenWeb(val url: String) : SystemActionType()
    data class SendEmail(val email: String, val subject: String? = null, val body: String? = null) :
        SystemActionType()

    data class Copy(val content: String) : SystemActionType()
    data object NotSupported : SystemActionType()
}
