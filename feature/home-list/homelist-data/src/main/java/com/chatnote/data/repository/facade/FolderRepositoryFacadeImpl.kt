package com.chatnote.data.repository.facade

import com.chatnote.coredomain.facade.FolderRepositoryFacade
import com.chatnote.coredomain.utils.ResultError
import com.chatnote.coredomain.utils.failure
import com.chatnote.data.db.FolderDao
import javax.inject.Inject

class FolderRepositoryFacadeImpl @Inject constructor(
    private val folderDao: FolderDao
) : FolderRepositoryFacade {

    override suspend fun updateFolderWithLastNote(
        folderId: Long,
        lastNote: String,
        lastNoteDate: Long
    ): Result<Unit> {
        return runCatching {
            folderDao.updateFolderLastNote(
                folderId = folderId,
                lastNote = lastNote,
                lastNoteDate = lastNoteDate
            )
        }.fold(
            onSuccess = { Result.success(Unit) },
            onFailure = { Result.failure(ResultError.DatabaseError(it)) }
        )
    }
}