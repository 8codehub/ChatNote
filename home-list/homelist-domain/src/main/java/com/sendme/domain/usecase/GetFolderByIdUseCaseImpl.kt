package com.sendme.domain.usecase

import com.sendme.domain.model.Folder
import com.sendme.domain.repository.FolderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class GetFolderByIdUseCaseImpl @Inject constructor(
    private val repository: FolderRepository
) : GetFolderByIdUseCase {
    override suspend  operator fun invoke(folderId: Long): Result<Folder> {
        return repository.getFolderById(folderId = folderId)
    }
}