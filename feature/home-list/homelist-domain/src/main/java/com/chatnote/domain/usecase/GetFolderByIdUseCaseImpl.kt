package com.chatnote.domain.usecase

import com.chatnote.domain.repository.FolderRepository
import javax.inject.Inject

internal class GetFolderByIdUseCaseImpl @Inject constructor(
    private val repository: FolderRepository
) : GetFolderByIdUseCase {

    override suspend operator fun invoke(folderId: Long) =
        repository.getFolderById(folderId = folderId)
}