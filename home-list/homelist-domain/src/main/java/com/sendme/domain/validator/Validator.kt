package com.sendme.domain.validator

import com.sendme.coredomain.navigation.validator.ValidationResult

interface NewFolderNameValidator {
    operator fun invoke(folderName: String?): ValidationResult
}