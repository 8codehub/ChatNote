package com.sendme.domain.repository

import com.sendme.domain.model.Folder
import kotlinx.coroutines.flow.Flow

interface FolderStreamRepository {
    fun getFolders(): Flow<List<Folder>>
}