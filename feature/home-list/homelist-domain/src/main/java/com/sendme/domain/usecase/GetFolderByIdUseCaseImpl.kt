package com.sendme.domain.usecase

import com.sendme.domain.repository.FolderRepository
import javax.inject.Inject

internal class GetFolderByIdUseCaseImpl @Inject constructor(
    private val repository: FolderRepository
) : GetFolderByIdUseCase {

    override suspend operator fun invoke(folderId: Long) =
        repository.getFolderById(folderId = folderId)
}