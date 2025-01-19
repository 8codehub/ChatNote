package com.sendme.directnotsdomain.usecase

import kotlinx.coroutines.flow.Flow
import com.sendme.directnotsdomain.SendMeNote
import com.sendme.directnotsdomain.repository.NotesRepository
import javax.inject.Inject

class GetNotesUseCaseImpl @Inject constructor(
    private val repository: NotesRepository
) : GetNotesUseCase {
    override fun invoke(folderId: Long): Flow<List<SendMeNote>> {
        return repository.getNotes(folderId)
    }
}