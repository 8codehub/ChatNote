package com.sendme.domain.validator

import com.pingpad.coredomain.utils.ResultError
import com.pingpad.coredomain.utils.failure
import javax.inject.Inject

class NewFolderNameValidatorImpl @Inject constructor() : NewFolderNameValidator {
    override fun invoke(folderName: String?): Result<Unit> {
        return if (folderName.isNullOrBlank()) {
            Result.failure(ResultError.EmptyFolderName)
        } else {
            Result.success(Unit)
        }
    }
}
