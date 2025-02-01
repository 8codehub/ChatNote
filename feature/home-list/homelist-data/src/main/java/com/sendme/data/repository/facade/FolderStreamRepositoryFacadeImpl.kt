package com.sendme.data.repository.facade

import com.pingpad.coredomain.facade.FolderStreamRepositoryFacade
import com.pingpad.coredomain.mapper.Mapper
import com.pingpad.coredomain.models.FolderBaseInfo
import com.sendme.data.db.FolderDao
import com.sendme.data.models.FolderEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FolderStreamRepositoryFacadeImpl @Inject constructor(
    private val folderDao: FolderDao,
    private val mapperFolderEntityToFolderBaseInfo: Mapper<FolderEntity, FolderBaseInfo>
) : FolderStreamRepositoryFacade {

    override fun observeFolderById(folderId: Long): Flow<FolderBaseInfo> {
        return mapperFolderEntityToFolderBaseInfo.mapSingleFlow(folderDao.observeFolderById(folderId = folderId))
    }
}