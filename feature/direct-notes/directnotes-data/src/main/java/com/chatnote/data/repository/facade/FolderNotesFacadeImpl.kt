package com.chatnote.data.repository.facade

import com.chatnote.coredomain.facade.NotesRepositoryFacade
import com.chatnote.coredomain.utils.ResultError
import com.chatnote.coredomain.utils.failure
import com.chatnote.data.db.NoteDao
import javax.inject.Inject

class FolderNotesFacadeImpl @Inject constructor(
    private val noteDao: NoteDao
) : NotesRepositoryFacade {

    override suspend fun deleteNotesByFolderId(folderId: Long): Result<Int> {
        return runCatching {
            noteDao.deleteNotesByFolderId(folderId = folderId)
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(ResultError.DatabaseError(it)) }
        )
    }
}
