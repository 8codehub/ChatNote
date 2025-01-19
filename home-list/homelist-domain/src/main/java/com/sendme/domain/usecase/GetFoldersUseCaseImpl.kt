package com.sendme.domain.usecase

import com.sendme.domain.model.Folder
import com.sendme.domain.repository.FolderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class GetFoldersUseCaseImpl @Inject constructor(
    private val repository: FolderRepository
) : GetFoldersUseCase {
    override operator fun invoke(): Flow<List<Folder>> {
        return repository.getFolders()
    }
}