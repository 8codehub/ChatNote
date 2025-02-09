package com.chatnote.data.repository.facade

import com.chatnote.coredata.di.db.FolderDao
import com.chatnote.coredata.di.model.FolderEntity
import com.chatnote.coredomain.facade.FolderStreamRepositoryFacade
import com.chatnote.coredomain.mapper.Mapper
import com.chatnote.coredomain.models.FolderBaseInfo
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