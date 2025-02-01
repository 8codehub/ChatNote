package com.sendme.directnotsdomain.usecase

import com.pingpad.coredomain.facade.FolderStreamRepositoryFacade
import javax.inject.Inject

class ObserveFolderUseCaseImpl @Inject constructor(
    private val folderRepositoryFacade: FolderStreamRepositoryFacade
) : ObserveFolderUseCase {

    override fun invoke(folderId: Long) =
        folderRepositoryFacade.observeFolderById(folderId = folderId)

}