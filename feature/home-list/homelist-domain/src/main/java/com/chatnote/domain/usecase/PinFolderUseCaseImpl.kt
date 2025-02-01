package com.chatnote.domain.usecase

import com.chatnote.domain.repository.FolderRepository
import javax.inject.Inject

internal class PinFolderUseCaseImpl @Inject constructor(
    private val repository: FolderRepository
) : PinFolderUseCase {

    override suspend fun invoke(folderId: Long) = repository.pinFolder(folderId = folderId)
}