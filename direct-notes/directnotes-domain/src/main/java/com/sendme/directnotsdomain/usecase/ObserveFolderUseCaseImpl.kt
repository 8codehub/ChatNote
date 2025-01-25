package com.sendme.directnotsdomain.usecase

import com.pingpad.coredomain.bridge.FolderRepositoryFacade
import com.pingpad.coredomain.models.FolderBaseInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveFolderUseCaseImpl @Inject constructor(
    private val folderRepositoryFacade: FolderRepositoryFacade
) : ObserveFolderUseCase {
    override fun invoke(folderId: Long): Flow<FolderBaseInfo?> {
        return folderRepositoryFacade.observeFolderById(folderId = folderId)
    }

}