package com.chatnote.directnotesdomain.usecase

import com.chatnote.directnotesdomain.model.Note
import com.chatnote.directnotesdomain.repository.NotesRepository
import javax.inject.Inject

internal class GetNoteByIdUseCaseImpl @Inject constructor(
    private val notesRepository: NotesRepository
) : GetNoteByIdUseCase {
    override suspend fun invoke(noteId: Long): Result<Note> {
        return notesRepository.getNoteById(noteId = noteId)
    }

}