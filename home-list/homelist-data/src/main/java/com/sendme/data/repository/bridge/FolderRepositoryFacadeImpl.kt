package com.sendme.data.repository.bridge

import com.pingpad.coredomain.navigation.bridge.FolderRepositoryFacade
import com.pingpad.coredomain.navigation.mapper.Mapper
import com.pingpad.coredomain.navigation.models.FolderBaseInfo
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
    ) {
        folderDao.updateFolderLastNote(
            folderId = folderId,
            lastNote = lastNote,
            lastNoteDate = lastNoteDate
        )
    }

    override fun observeFolderById(folderId: Long): Flow<FolderBaseInfo> {
        return mapperFolderEntityToFolderBaseInfo.mapSingleFlow(folderDao.observeFolderById(folderId = folderId))
    }
}