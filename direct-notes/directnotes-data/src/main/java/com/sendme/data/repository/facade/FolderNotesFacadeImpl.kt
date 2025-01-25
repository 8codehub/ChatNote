package com.sendme.data.repository.facade

import com.pingpad.coredomain.bridge.NotesRepositoryFacade
import com.pingpad.coredomain.utils.ResultError
import com.pingpad.coredomain.utils.failure
import com.sendme.data.db.NoteDao
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
