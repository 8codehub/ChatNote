package com.sendme.data.repository


import com.pingpad.coredomain.navigation.mapper.Mapper
import com.sendme.data.db.FolderDao
import com.sendme.data.models.FolderEntity
import com.sendme.domain.model.Folder
import com.sendme.domain.repository.FolderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FolderRepositoryImpl @Inject constructor(
    private val folderDao: FolderDao,
    private val mapperFolderToFolderEntity: Mapper<Folder, FolderEntity>,
    private val mapperFolderEntityToFolder: Mapper<FolderEntity, Folder>,
) : FolderRepository {

    override suspend fun addOrUpdateFolder(
        folderId: Long?,
        name: String,
        iconUri: String
    ): Long {
        return if (folderId == null) {
            folderDao.insertOrReplaceFolder(
                FolderEntity(
                    name = name,
                    iconUri = iconUri,
                    lastNoteContent = null,
                    lastNoteCreatedAt = null
                )
            )
        } else {
            val updatedNote =
                folderDao.getFolderById(folderId)?.copy(name = name, iconUri = iconUri)
            updatedNote?.let {
                folderDao.insertOrReplaceFolder(
                    it
                )
            } ?: 0
        }
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

    override suspend fun getFolderById(folderId: Long): Result<Folder> {
        return try {
            val folderEntity = folderDao.getFolderById(folderId)
            if (folderEntity != null) {
                val folder = mapperFolderEntityToFolder.map(folderEntity)
                Result.success(folder)
            } else {
                Result.failure(Exception("Folder not found"))
            }
        } catch (e: Exception) {
            Result.failure(e) // Handle unexpected errors
        }
    }

    override fun getFolders(): Flow<List<Folder>> {
        return mapperFolderEntityToFolder.mapFlow(folderDao.getAllFolders())
    }
}