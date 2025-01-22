package com.sendme.data.repository


import com.sendme.coredomain.navigation.mapper.Mapper
import com.sendme.data.db.FolderDao
import com.sendme.data.models.FolderEntity
import com.sendme.domain.model.Folder
import com.sendme.domain.repository.FolderRepository
import dagger.Provides
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

class FolderRepositoryImpl @Inject constructor(
    private val folderDao: FolderDao,
    private val mapperFolderToFolderEntity: Mapper<Folder, FolderEntity>,
    private val mapperFolderEntityToFolder: Mapper<FolderEntity, Folder>,
) : FolderRepository {

    override suspend fun createFolder(folderName: String, iconUri: String): Long {
        return folderDao.insertFolder(
            FolderEntity(
                name = folderName,
                lastNoteContent = "",
                iconUri = iconUri,
                lastNoteCreatedAt = System.currentTimeMillis()
            )
        )
    }

    override suspend fun pinFolder(folderId: Long): Result<Unit> {
        return try {
            val rowsUpdated = folderDao.pinFolder(folderId)
            if (rowsUpdated > 0) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Folder not found or already pinned")) // Failure
            }
        } catch (e: Exception) {
            Result.failure(e) // Handle unexpected errors
        }
    }

    override suspend fun unpinFolder(folderId: Long): Result<Unit> {
        return try {
            val rowsUpdated = folderDao.unpinFolder(folderId)
            if (rowsUpdated > 0) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Folder not found or already pinned")) // Failure
            }
        } catch (e: Exception) {
            Result.failure(e) // Handle unexpected errors
        }
    }

    override fun getFolders(): Flow<List<Folder>> {
        return mapperFolderEntityToFolder.mapFlow(folderDao.getAllFolders())
    }
}