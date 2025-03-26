package com.chatnote.directnotesdomain.usecase

import com.chatnote.directnotesdomain.repository.NotesRepository
import javax.inject.Inject

internal class UpdateNoteContentUseCaseImpl @Inject constructor(
    private val notesRepository: NotesRepository
) : UpdateNoteContentUseCase {

    override suspend fun invoke(noteId: Long, content: String): Result<Unit> {
        return notesRepository.updateNoteContent(noteId = noteId, content = content)
    }
}