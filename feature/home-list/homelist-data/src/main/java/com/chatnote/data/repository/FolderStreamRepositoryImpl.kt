package com.chatnote.data.repository

import com.chatnote.coredomain.mapper.Mapper
import com.chatnote.data.db.FolderDao
import com.chatnote.data.models.FolderEntity
import com.chatnote.domain.model.Folder
import com.chatnote.domain.repository.FolderStreamRepository
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