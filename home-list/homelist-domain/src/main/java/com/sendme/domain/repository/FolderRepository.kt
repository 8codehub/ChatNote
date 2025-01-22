package com.sendme.domain.repository

import com.sendme.domain.model.Folder
import kotlinx.coroutines.flow.Flow

interface FolderRepository {
    suspend fun createFolder(folderName: String, iconUri: String): Long
    suspend fun pinFolder(folderId: Long): Result<Unit>
    suspend fun unpinFolder(folderId: Long): Result<Unit>
    fun getFolders(): Flow<List<Folder>>
}