package com.sendme.directnotsdomain.usecase

import com.pingpad.coredomain.navigation.bridge.FolderRepositoryFacade
import com.pingpad.coredomain.navigation.models.FolderBaseInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveFolderUseCaseImpl @Inject constructor(
    private val folderRepositoryFacade: FolderRepositoryFacade
) : ObserveFolderUseCase {
    override fun invoke(folderId: Long): Flow<FolderBaseInfo?> {
        return folderRepositoryFacade.observeFolderById(folderId = folderId)
    }

}