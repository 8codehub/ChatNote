package com.sendme.directnotsdomain.usecase

import com.sendme.directnotsdomain.model.Note
import com.sendme.directnotsdomain.repository.NotesRepository
import javax.inject.Inject

class AddNoteUseCaseImpl @Inject constructor(
    private val repository: NotesRepository
) : AddNoteUseCase {

    override suspend fun invoke(folderId: Long, note: Note) =
        repository.addNote(folderId, note)
}