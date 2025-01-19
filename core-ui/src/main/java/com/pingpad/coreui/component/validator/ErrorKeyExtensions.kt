package com.sendme.coreui.component.validator

import com.sendme.coreui.R
import com.sendme.coredomain.navigation.validator.ErrorKey

fun ErrorKey.toCoreErrorStringId(): Int? {
    return when (this) {
        ErrorKey.EMPTY_TEXT -> R.string.validation_error_empty_text
        ErrorKey.ERROR -> R.string.validation_error
        else -> null
    }
}