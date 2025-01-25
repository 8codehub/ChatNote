package com.sendme.domain.usecase

import com.sendme.domain.repository.FolderRepository
import javax.inject.Inject

internal class GetFoldersUseCaseImpl @Inject constructor(
    private val repository: FolderRepository
) : GetFoldersUseCase {

    override operator fun invoke() = repository.getFolders()
}