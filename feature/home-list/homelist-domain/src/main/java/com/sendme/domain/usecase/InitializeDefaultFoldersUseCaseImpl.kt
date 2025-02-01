package com.sendme.domain.usecase

import com.sendme.domain.model.DefaultFolder
import com.sendme.domain.repository.FolderRepository
import javax.inject.Inject

class InitializeDefaultFoldersUseCaseImpl @Inject constructor(
    private val folderRepository: FolderRepository,
) : InitializeDefaultFoldersUseCase {

    override suspend fun invoke(defaultFolders: List<DefaultFolder>) {
        defaultFolders.forEach { defaultFolder ->
            folderRepository.addOrUpdateFolder(
                name = defaultFolder.name,
                iconUri = defaultFolder.iconUri
            )

        }
    }

}