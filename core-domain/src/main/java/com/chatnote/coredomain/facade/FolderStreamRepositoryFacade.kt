package com.chatnote.coredomain.facade

import com.chatnote.coredomain.models.FolderBaseInfo
import kotlinx.coroutines.flow.Flow

interface FolderStreamRepositoryFacade {

    fun observeFolderById(folderId: Long): Flow<FolderBaseInfo>
}
