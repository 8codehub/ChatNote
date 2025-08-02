package com.chatnote.directnotesdomain.validator

import com.chatnote.directnotesdomain.model.Note

interface EditNoteContentValidator {

    operator fun invoke(content: String?): Result<Unit>
}


interface NewNoteValidator {

    operator fun invoke(note: Note): Result<Unit>
}