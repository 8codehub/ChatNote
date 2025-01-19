package com.sendme.domain.validator

import com.sendme.coredomain.navigation.validator.ErrorKey
import com.sendme.coredomain.navigation.validator.ValidationResult
import javax.inject.Inject

class NewFolderNameValidatorImpl @Inject constructor() : NewFolderNameValidator {
    override fun invoke(folderName: String?): ValidationResult {
        return when {
            folderName.isNullOrBlank() -> {
                ValidationResult.Error(errorKey = ErrorKey.EMPTY_FOLDER_NAME)
            }

            else -> ValidationResult.Success
        }
    }
}