package com.chatnote.coreui.systemactions

interface IntentActions {
    fun openInBrowser(url: String)
    fun openDialer(phoneNumber: String)
    fun sendSms(phoneNumber: String)
    fun shareText(text: String)
    fun sendEmail(email: String)
}
