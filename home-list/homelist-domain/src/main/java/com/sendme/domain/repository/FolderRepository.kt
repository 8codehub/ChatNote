package com.sendme.domain.repository

import com.sendme.domain.model.Folder
import kotlinx.coroutines.flow.Flow

interface FolderRepository {
    suspend fun addOrUpdateFolder(folderId: Long?, name: String, iconUri: String): Result<Long>
    suspend fun pinFolder(folderId: Long): Result<Unit>
    suspend fun deleteFolder(folderId: Long): Result<Unit>
    suspend fun unpinFolder(folderId: Long): Result<Unit>
    suspend fun getFolderById(folderId: Long): Result<Folder>
}