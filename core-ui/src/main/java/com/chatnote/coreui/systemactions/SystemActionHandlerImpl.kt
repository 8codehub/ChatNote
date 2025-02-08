package com.chatnote.coreui.systemactions

import android.content.Context
import chatnote.coreui.R
import com.chatnote.coreui.model.SystemActionType
import com.chatnote.coreui.ui.decorations.showToast
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SystemActionHandlerImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val clipboardActions: ClipboardActions,
    private val intentActions: IntentActions
) : SystemActionTypeHandler {

    override fun handleAction(systemActionType: SystemActionType) {
        when (systemActionType) {
            is SystemActionType.Copy -> clipboardActions.copyToClipboard(systemActionType.content)
            is SystemActionType.OpenWeb -> intentActions.openInBrowser(systemActionType.url)
            is SystemActionType.Call -> intentActions.openDialer(systemActionType.phoneNumber)
            is SystemActionType.SMS -> intentActions.sendSms(systemActionType.phoneNumber)
            is SystemActionType.Share -> intentActions.shareText(systemActionType.content)
            is SystemActionType.SendEmail -> intentActions.sendEmail(systemActionType.email)
            else -> showToast(
                context = context,
                message = context.getString(R.string.action_not_supported)
            )
        }
    }
}