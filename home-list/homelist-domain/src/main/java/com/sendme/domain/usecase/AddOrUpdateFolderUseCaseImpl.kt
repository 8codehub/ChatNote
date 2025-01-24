package com.sendme.domain.usecase

import com.sendme.domain.repository.FolderRepository
import javax.inject.Inject

internal class AddOrUpdateFolderUseCaseImpl @Inject constructor(
    private val repository: FolderRepository
) : AddOrUpdateFolderUseCase {
    override suspend operator fun invoke(folderId: Long?, name: String, iconUri: String): Long {
        return repository.addOrUpdateFolder(folderId = folderId, name = name, iconUri = iconUri)
    }
}