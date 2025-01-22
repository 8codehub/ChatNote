package com.sendme.domain.usecase

import com.sendme.domain.repository.FolderRepository
import javax.inject.Inject

internal class PinFolderUseCaseImpl @Inject constructor(
    private val repository: FolderRepository
) : PinFolderUseCase {

    override suspend fun invoke(folderId: Long): Result<Unit> {
        return repository.pinFolder(folderId = folderId)
    }
}