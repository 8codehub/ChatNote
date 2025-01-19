package com.sendme.domain.repository

import com.sendme.domain.model.Folder
import kotlinx.coroutines.flow.Flow

interface FolderRepository {
    suspend fun createFolder(folderName: String, iconUri: String): Long
    fun getFolders(): Flow<List<Folder>>
}