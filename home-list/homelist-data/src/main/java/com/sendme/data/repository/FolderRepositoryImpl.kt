package com.sendme.data.repository


import com.pingpad.coredomain.utils.ResultError
import com.pingpad.coredomain.utils.failure
import com.pingpad.coredomain.mapper.Mapper
import com.sendme.data.db.FolderDao
import com.sendme.data.models.FolderEntity
import com.sendme.domain.model.Folder
import com.sendme.domain.repository.FolderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FolderRepositoryImpl @Inject constructor(
    private val folderDao: FolderDao,
    private val mapperFolderEntityToFolder: Mapper<FolderEntity, Folder>,
) : FolderRepository {

    override suspend fun addOrUpdateFolder(
        folderId: Long?,
        name: String,
        iconUri: String
    ): Result<Long> {
        return try {
            val folderIdResult = if (folderId == null) {
                // Insert new folder
                folderDao.insertOrReplaceFolder(
                    FolderEntity(
                        name = name,
                        iconUri = iconUri,
                        lastNoteContent = null,
                        lastNoteCreatedAt = null
                    )
                )
            } else {
                val existingFolder = folderDao.getFolderById(folderId)
                if (existingFolder != null) {
                    folderDao.insertOrReplaceFolder(
                        existingFolder.copy(name = name, iconUri = iconUri)
                    )
                } else {
                    return Result.failure(ResultError.FolderNotFound)
                }
            }
            Result.success(folderIdResult)
        } catch (e: Exception) {
            Result.failure(ResultError.DatabaseError(th = e))
        }
    }

    override suspend fun pinFolder(folderId: Long): Result<Unit> {
        return try {
            val rowsUpdated = folderDao.pinFolder(folderId)
            if (rowsUpdated > 0) {
                Result.success(Unit)
            } else {
                Result.failure(ResultError.FolderNotFound)
            }
        } catch (e: Exception) {
            Result.failure(ResultError.DatabaseError(th = e))
        }
    }

    override suspend fun deleteFolder(folderId: Long): Result<Unit> {
        return try {
            val rowsUpdated = folderDao.deleteFolder(folderId)
            if (rowsUpdated > 0) {
                Result.success(Unit)
            } else {
                Result.failure(ResultError.FolderNotFound)
            }
        } catch (e: Exception) {
            Result.failure(ResultError.DatabaseError(th = e))
        }
    }

    override suspend fun unpinFolder(folderId: Long): Result<Unit> {
        return try {
            val rowsUpdated = folderDao.unpinFolder(folderId)
            if (rowsUpdated > 0) {
                Result.success(Unit)
            } else {
                Result.failure(ResultError.FolderNotFound)
            }
        } catch (e: Exception) {
            Result.failure(ResultError.DatabaseError(th = e))
        }
    }

    override suspend fun getFolderById(folderId: Long): Result<Folder> {
        return try {
            val folderEntity = folderDao.getFolderById(folderId)
            if (folderEntity != null) {
                val folder = mapperFolderEntityToFolder.map(folderEntity)
                Result.success(folder)
            } else {
                Result.failure(ResultError.FolderNotFound)
            }
        } catch (e: Exception) {
            Result.failure(ResultError.DatabaseError(th = e))
        }
    }

    override fun getFolders(): Flow<List<Folder>> {
        return mapperFolderEntityToFolder.mapFlow(folderDao.observeAllFolders())
    }
}