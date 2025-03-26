package com.chatnote.directnotesdomain.validator

import com.chatnote.coredomain.utils.ResultError
import com.chatnote.coredomain.utils.failure
import javax.inject.Inject

internal class EditNoteContentValidatorImpl @Inject constructor() : EditNoteContentValidator {
    override fun invoke(content: String?) = if (content.isNullOrBlank()) {
        Result.failure(ResultError.EmptyNoteContent)
    } else {
        Result.success(Unit)
    }
}
