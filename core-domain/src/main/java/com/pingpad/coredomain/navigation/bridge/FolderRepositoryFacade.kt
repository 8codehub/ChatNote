package com.pingpad.coredomain.navigation.bridge

import com.pingpad.coredomain.navigation.models.FolderBaseInfo
import kotlinx.coroutines.flow.Flow

interface FolderRepositoryFacade {

    fun observeFolderById(folderId: Long): Flow<FolderBaseInfo>

    suspend fun updateFolderWithLastNote(folderId: Long, lastNote: String, lastNoteDate: Long)

}
