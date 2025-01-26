package com.pingpad.coredomain.facade

import com.pingpad.coredomain.models.FolderBaseInfo
import kotlinx.coroutines.flow.Flow

interface FolderStreamRepositoryFacade {

    fun observeFolderById(folderId: Long): Flow<FolderBaseInfo>
}
