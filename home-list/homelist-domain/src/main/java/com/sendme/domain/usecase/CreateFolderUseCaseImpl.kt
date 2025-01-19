package com.sendme.domain.usecase

import com.sendme.domain.repository.FolderRepository
import javax.inject.Inject

internal class CreateFolderUseCaseImpl @Inject constructor(
    private val repository: FolderRepository
) : CreateFolderUseCase {
    override suspend operator fun invoke(folderName: String, iconUri: String): Long {
        return repository.createFolder(folderName = folderName, iconUri = iconUri)
    }
}