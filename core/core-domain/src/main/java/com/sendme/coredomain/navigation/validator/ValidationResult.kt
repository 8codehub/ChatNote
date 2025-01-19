package com.sendme.coredomain.navigation.validator

sealed class ValidationResult {
    data object Success : ValidationResult()
    data class Error(val errorKey: ErrorKey) : ValidationResult()
}

enum class ErrorKey {
    ERROR, EMPTY_TEXT, EMPTY_NAME, EMPTY_FOLDER_NAME
}
