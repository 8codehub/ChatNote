package com.chatnote.data.repository

import com.chatnote.coredomain.mapper.Mapper
import com.chatnote.coredomain.utils.ResultError
import com.chatnote.coredomain.utils.failure
import com.chatnote.coredomain.utils.throwAsAppException
import com.chatnote.domain.model.Folder
import com.chatnote.domain.repository.FolderRepository
import com.chatnote.data.db.FolderDao
import com.chatnote.data.models.FolderEntity
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
        return runCatching {
            val folderIdResult = if (folderId == null) {
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
                    ?: ResultError.FolderNotFound.throwAsAppException()
                folderDao.insertOrReplaceFolder(existingFolder.copy(name = name, iconUri = iconUri))
            }
            folderIdResult
        }.onSuccess {
            return Result.success(it)
        }.onFailure {
            return Result.failure(ResultError.DatabaseError(it))
        }
    }

    override suspend fun pinFolder(folderId: Long): Result<Unit> {
        return runCatching {
            val rowsUpdated = folderDao.pinFolder(folderId)
            if (rowsUpdated <= 0) ResultError.FolderNotFound.throwAsAppException()
        }.onSuccess {
            return Result.success(Unit)
        }.onFailure {
            return Result.failure(ResultError.DatabaseError(it))
        }
    }

    override suspend fun deleteFolder(folderId: Long): Result<Unit> {
        return runCatching {
            val rowsDeleted = folderDao.deleteFolder(folderId)
            if (rowsDeleted <= 0) ResultError.FolderNotFound.throwAsAppException()
        }.onSuccess {
            return Result.success(Unit)
        }.onFailure {
            return Result.failure(ResultError.DatabaseError(it))
        }
    }

    override suspend fun unpinFolder(folderId: Long): Result<Unit> {
        return runCatching {
            val rowsUpdated = folderDao.unpinFolder(folderId)
            if (rowsUpdated <= 0) ResultError.FolderNotFound.throwAsAppException()
        }.onSuccess {
            return Result.success(Unit)
        }.onFailure {
            return Result.failure(ResultError.DatabaseError(it))
        }
    }

    override suspend fun getFolderById(folderId: Long): Result<Folder> {
        return runCatching {
            val folderEntity = folderDao.getFolderById(folderId)
                ?: ResultError.FolderNotFound.throwAsAppException()
            mapperFolderEntityToFolder.map(folderEntity)
        }.onSuccess {
            return Result.success(it)
        }.onFailure {
            return Result.failure(ResultError.DatabaseError(it))
        }
    }
}
