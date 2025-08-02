package com.chatnote.directnotesdomain.validator

import com.chatnote.coredomain.utils.ResultError
import com.chatnote.coredomain.utils.failure
import com.chatnote.directnotesdomain.model.Note
import javax.inject.Inject

class NewNoteValidatorImpl @Inject constructor() : NewNoteValidator {
    override fun invoke(note: Note) =
        if (note.content.isBlank() && note.extras.isEmpty()) {
            Result.failure(ResultError.EmptyNoteContent)
        } else {
            Result.success(Unit)
        }
}