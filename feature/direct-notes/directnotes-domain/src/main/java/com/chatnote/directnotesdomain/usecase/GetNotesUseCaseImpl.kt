package com.chatnote.directnotesdomain.usecase

import com.chatnote.directnotesdomain.repository.NotesStreamRepository
import javax.inject.Inject

class GetNotesUseCaseImpl @Inject constructor(
    private val repository: NotesStreamRepository
) : GetNotesUseCase {

    override fun invoke(folderId: Long) = repository.observeNotes(folderId)
}