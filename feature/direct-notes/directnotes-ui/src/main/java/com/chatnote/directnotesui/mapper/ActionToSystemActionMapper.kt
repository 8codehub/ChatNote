package com.chatnote.directnotesui.mapper

import com.chatnote.coredomain.mapper.Mapper
import com.chatnote.coreui.model.SystemActionType
import com.chatnote.coreui.model.SystemActionableItem
import com.chatnote.directnotesui.actionablesheet.action.UiAction
import com.chatnote.directnotesui.model.UiActionType
import javax.inject.Inject


class ActionTypeToSystemActionTypeMapper @Inject constructor() :
    Mapper<UiAction, SystemActionableItem> {

    override fun map(from: UiAction): SystemActionableItem {
        val actionType = when (from.action) {
            UiActionType.Call -> SystemActionType.Call
            UiActionType.Copy -> SystemActionType.Copy
            UiActionType.SMS -> SystemActionType.SMS
            UiActionType.Share -> SystemActionType.Share
            UiActionType.OpenWeb -> SystemActionType.OpenWeb
            UiActionType.OpenEmail -> SystemActionType.SendEmail
        }
        return SystemActionableItem(content = from.content, action = actionType)
    }
}
