package com.chatnote.coreui.systemactions

import com.chatnote.coreui.model.SystemActionType

interface SystemActionTypeHandler {
    fun handleAction(systemActionType: SystemActionType)
}