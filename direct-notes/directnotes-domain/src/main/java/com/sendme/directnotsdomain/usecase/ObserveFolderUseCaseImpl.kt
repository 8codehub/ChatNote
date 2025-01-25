package com.sendme.directnotsdomain.usecase

import com.pingpad.coredomain.facade.FolderRepositoryFacade
import javax.inject.Inject

class ObserveFolderUseCaseImpl @Inject constructor(
    private val folderRepositoryFacade: FolderRepositoryFacade
) : ObserveFolderUseCase {

    override fun invoke(folderId: Long) =
        folderRepositoryFacade.observeFolderById(folderId = folderId)

}