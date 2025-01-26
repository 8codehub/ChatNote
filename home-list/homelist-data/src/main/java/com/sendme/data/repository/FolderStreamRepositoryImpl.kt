package com.sendme.data.repository

import com.pingpad.coredomain.mapper.Mapper
import com.sendme.data.db.FolderDao
import com.sendme.data.models.FolderEntity
import com.sendme.domain.model.Folder
import com.sendme.domain.repository.FolderStreamRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FolderStreamRepositoryImpl @Inject constructor(
    private val folderDao: FolderDao,
    private val mapperFolderEntityToFolder: Mapper<FolderEntity, Folder>,
) : FolderStreamRepository {
    override fun getFolders(): Flow<List<Folder>> {
        return mapperFolderEntityToFolder.mapFlow(folderDao.observeAllFolders())
    }
}