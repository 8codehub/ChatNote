package com.chatnote.directnotesdomain.usecase

import com.chatnote.directnotesdomain.model.Note
import com.chatnote.directnotesdomain.repository.NotesRepository

import javax.inject.Inject

internal class AddNoteUseCaseImpl @Inject constructor(
    private val repository: NotesRepository
) : AddNoteUseCase {

    override suspend fun invoke(folderId: Long, note: Note) =
        repository.addNote(folderId, note)
}