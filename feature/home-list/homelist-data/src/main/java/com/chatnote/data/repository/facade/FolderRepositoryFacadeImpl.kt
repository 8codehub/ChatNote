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
        lastNoteId: Long,
        lastNoteDate: Long
    ): Result<Unit> {
        return runCatching {
            folderDao.updateFolderLastNote(
                folderId = folderId,
                lastNote = lastNote,
                lastNoteId = lastNoteId,
                lastNoteDate = lastNoteDate
            )
        }.fold(
            onSuccess = { Result.success(Unit) },
            onFailure = { Result.failure(ResultError.DatabaseError(it)) }
        )
    }

    override suspend fun deleteFolderLastNoteIfMatch(folderId: Long, noteId: Long): Result<Unit> {
        return kotlin.runCatching {
            folderDao.clearFolderLastNoteIfMatch(folderId = folderId, noteId = noteId)
        }.fold(
            onSuccess = {
                Result.success(Unit)
            },
            onFailure = {
                Result.failure(ResultError.DatabaseError(it))
            }
        )
    }
}