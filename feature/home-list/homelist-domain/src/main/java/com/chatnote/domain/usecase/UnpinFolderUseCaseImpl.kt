package com.chatnote.domain.usecase

import com.chatnote.domain.repository.FolderRepository
import javax.inject.Inject

internal class UnpinFolderUseCaseImpl @Inject constructor(
    private val repository: FolderRepository
) : UnpinFolderUseCase {

    override suspend fun invoke(folderId: Long) = repository.unpinFolder(folderId = folderId)
}