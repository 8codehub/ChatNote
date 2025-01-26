package com.sendme.data.repository.facade

import com.pingpad.coredomain.facade.FolderRepositoryFacade
import com.pingpad.coredomain.utils.ResultError
import com.pingpad.coredomain.utils.failure
import com.sendme.data.db.FolderDao
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