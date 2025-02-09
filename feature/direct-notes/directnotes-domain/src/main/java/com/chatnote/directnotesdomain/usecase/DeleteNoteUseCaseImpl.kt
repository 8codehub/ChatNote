package com.chatnote.directnotesdomain.usecase

import com.chatnote.directnotesdomain.repository.NotesRepository
import javax.inject.Inject

class DeleteNoteUseCaseImpl @Inject constructor(
    private val notesRepository: NotesRepository
) : DeleteNoteUseCase {
    override suspend fun invoke(noteId: Long): Result<Unit> {

        return notesRepository.deleteNote(noteId = noteId)
    }
}