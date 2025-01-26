package com.sendme.directnotsdomain.usecase

import com.sendme.directnotsdomain.repository.NotesStreamRepository
import javax.inject.Inject

class GetNotesUseCaseImpl @Inject constructor(
    private val repository: NotesStreamRepository
) : GetNotesUseCase {

    override fun invoke(folderId: Long) = repository.observeNotes(folderId)
}