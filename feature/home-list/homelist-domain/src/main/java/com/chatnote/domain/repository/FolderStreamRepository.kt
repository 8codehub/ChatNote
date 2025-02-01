package com.chatnote.domain.repository

import com.chatnote.domain.model.Folder
import kotlinx.coroutines.flow.Flow

interface FolderStreamRepository {
    fun getFolders(): Flow<List<Folder>>
}