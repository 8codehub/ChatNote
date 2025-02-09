package com.chatnote.domain.validator

import com.chatnote.coredomain.utils.ResultError
import com.chatnote.coredomain.utils.failure
import javax.inject.Inject

internal class NewFolderNameValidatorImpl @Inject constructor() : NewFolderNameValidator {
    override fun invoke(folderName: String?) = if (folderName.isNullOrBlank()) {
        Result.failure(ResultError.EmptyFolderName)
    } else {
        Result.success(Unit)
    }
}
