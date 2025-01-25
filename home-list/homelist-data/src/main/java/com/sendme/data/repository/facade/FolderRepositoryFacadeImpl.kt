package com.sendme.data.repository.facade

import com.pingpad.coredomain.bridge.FolderRepositoryFacade
import com.pingpad.coredomain.mapper.Mapper
import com.pingpad.coredomain.models.FolderBaseInfo
import com.pingpad.coredomain.utils.ResultError
import com.pingpad.coredomain.utils.failure
import com.sendme.data.db.FolderDao
import com.sendme.data.models.FolderEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FolderRepositoryFacadeImpl @Inject constructor(
    private val folderDao: FolderDao,
    private val mapperFolderEntityToFolderBaseInfo: Mapper<FolderEntity, FolderBaseInfo>
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

    override fun observeFolderById(folderId: Long): Flow<FolderBaseInfo> {
        return mapperFolderEntityToFolderBaseInfo.mapSingleFlow(folderDao.observeFolderById(folderId = folderId))
    }
}