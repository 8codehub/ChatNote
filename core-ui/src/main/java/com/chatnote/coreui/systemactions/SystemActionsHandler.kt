package com.chatnote.coreui.systemactions

import com.chatnote.coreui.model.SystemActionableItem

interface SystemActionsHandler {
    fun handleAction(systemActionableItem: SystemActionableItem)
}