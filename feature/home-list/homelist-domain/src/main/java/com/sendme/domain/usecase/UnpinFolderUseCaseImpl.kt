package com.sendme.domain.usecase

import com.sendme.domain.repository.FolderRepository
import javax.inject.Inject

internal class UnpinFolderUseCaseImpl @Inject constructor(
    private val repository: FolderRepository
) : UnpinFolderUseCase {

    override suspend fun invoke(folderId: Long) = repository.unpinFolder(folderId = folderId)
}