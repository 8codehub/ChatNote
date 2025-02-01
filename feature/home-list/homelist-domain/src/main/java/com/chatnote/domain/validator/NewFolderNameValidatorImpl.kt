package com.chatnote.domain.validator

import com.chatnote.coredomain.utils.ResultError
import com.chatnote.coredomain.utils.failure
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
