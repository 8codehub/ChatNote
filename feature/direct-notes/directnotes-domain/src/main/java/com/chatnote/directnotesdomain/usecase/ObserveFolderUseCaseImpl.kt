package com.chatnote.directnotesdomain.usecase

import com.chatnote.coredomain.facade.FolderStreamRepositoryFacade
import javax.inject.Inject

class ObserveFolderUseCaseImpl @Inject constructor(
    private val folderRepositoryFacade: FolderStreamRepositoryFacade
) : ObserveFolderUseCase {

    override fun invoke(folderId: Long) =
        folderRepositoryFacade.observeFolderById(folderId = folderId)
}