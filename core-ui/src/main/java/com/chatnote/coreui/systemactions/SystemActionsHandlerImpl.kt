package com.chatnote.coreui.systemactions

import android.content.Context
import chatnote.coreui.R
import com.chatnote.coreui.model.SystemActionType
import com.chatnote.coreui.model.SystemActionableItem
import com.chatnote.coreui.ui.decorations.showToast
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SystemActionsHandlerImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val clipboardActions: ClipboardActions,
    private val intentActions: IntentActions
) : SystemActionsHandler {

    override fun handleAction(systemActionableItem: SystemActionableItem) {
        when (systemActionableItem.action) {
            SystemActionType.Copy -> clipboardActions.copyToClipboard(systemActionableItem.content)
            SystemActionType.OpenWeb -> intentActions.openInBrowser(systemActionableItem.content)
            SystemActionType.Call -> intentActions.openDialer(systemActionableItem.content)
            SystemActionType.SMS -> intentActions.sendSms(systemActionableItem.content)
            SystemActionType.Share -> intentActions.shareText(systemActionableItem.content)
            SystemActionType.SendEmail -> intentActions.sendEmail(systemActionableItem.content)
            else -> showToast(
                context = context,
                message = context.getString(R.string.action_not_supported)
            )
        }
    }
}