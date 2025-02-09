package com.chatnote.data.repository.facade

import com.chatnote.coredata.di.db.FolderDao
import com.chatnote.coredata.di.model.FolderEntity
import com.chatnote.coredomain.facade.FolderStreamRepositoryFacade
import com.chatnote.coredomain.mapper.Mapper
import com.chatnote.coredomain.models.FolderBaseInfo
import javax.inject.Inject

internal class FolderStreamRepositoryFacadeImpl @Inject constructor(
    private val folderDao: FolderDao,
    private val mapperFolderEntityToFolderBaseInfo: Mapper<FolderEntity, FolderBaseInfo>
) : FolderStreamRepositoryFacade {

    override fun observeFolderById(folderId: Long) =
        mapperFolderEntityToFolderBaseInfo.mapSingleFlow(folderDao.observeFolderById(folderId = folderId))
}