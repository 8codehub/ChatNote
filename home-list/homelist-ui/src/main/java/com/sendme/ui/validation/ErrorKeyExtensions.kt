package com.sendme.ui.validation

import com.sendme.coredomain.navigation.validator.ErrorKey
import com.sendme.coreui.component.validator.toCoreErrorStringId
import com.sendme.homelistui.R

private fun ErrorKey.toFeatureErrorStringId(): Int? {
    return when (this) {
        ErrorKey.EMPTY_NAME -> R.string.validation_error_empty_name
        ErrorKey.EMPTY_FOLDER_NAME -> R.string.validation_error_empty_folder_name
        else -> null
    }
}

fun ErrorKey.toErrorStringId(): Int {
    return toFeatureErrorStringId()
        ?: toCoreErrorStringId()
        ?: com.sendme.coreui.R.string.validation_error
}