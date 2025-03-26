package com.chatnote.directnotesdomain.validator

interface EditNoteContentValidator {

    operator fun invoke(content: String?): Result<Unit>
}