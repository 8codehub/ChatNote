package com.chatnote.data.repository.facade

import com.chatnote.coredomain.facade.FolderStreamRepositoryFacade
import com.chatnote.coredomain.mapper.Mapper
import com.chatnote.coredomain.models.FolderBaseInfo
import com.chatnote.data.db.FolderDao
import com.chatnote.data.models.FolderEntity
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