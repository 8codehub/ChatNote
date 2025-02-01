package com.chatnote.domain.usecase

import com.chatnote.domain.model.DefaultFolder
import com.chatnote.domain.repository.FolderRepository
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