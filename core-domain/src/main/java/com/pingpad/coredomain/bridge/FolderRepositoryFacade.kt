package com.pingpad.coredomain.bridge

import com.pingpad.coredomain.models.FolderBaseInfo
import kotlinx.coroutines.flow.Flow

interface FolderRepositoryFacade {

    fun observeFolderById(folderId: Long): Flow<FolderBaseInfo>

    suspend fun updateFolderWithLastNote(
        folderId: Long,
        lastNote: String,
        lastNoteDate: Long
    )
}
