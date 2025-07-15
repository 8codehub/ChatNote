package com.chatnote.data.repository.facade

import com.chatnote.coredata.di.db.NoteDAO
import com.chatnote.coredomain.facade.NotesRepositoryFacade
import com.chatnote.coredomain.utils.ResultError
import com.chatnote.coredomain.utils.failure
import javax.inject.Inject

internal class FolderNotesFacadeImpl @Inject constructor(
    private val noteDao: NoteDAO
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
