package com.sendme.domain.usecase

import com.sendme.domain.repository.FolderStreamRepository
import javax.inject.Inject

internal class GetFoldersUseCaseImpl @Inject constructor(
    private val repository: FolderStreamRepository
) : GetFoldersUseCase {

    override operator fun invoke() = repository.getFolders()
}