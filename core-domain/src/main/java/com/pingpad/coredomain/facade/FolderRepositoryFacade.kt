package com.pingpad.coredomain.facade

import com.pingpad.coredomain.models.FolderBaseInfo
import kotlinx.coroutines.flow.Flow

interface FolderRepositoryFacade {

    fun observeFolderById(folderId: Long): Flow<FolderBaseInfo>

    suspend fun updateFolderWithLastNote(
        folderId: Long,
        lastNote: String,
        lastNoteDate: Long
    ): Result<Unit>
}
