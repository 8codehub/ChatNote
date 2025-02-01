package com.chatnote.domain.usecase

import com.chatnote.domain.repository.FolderStreamRepository
import javax.inject.Inject

internal class GetFoldersUseCaseImpl @Inject constructor(
    private val repository: FolderStreamRepository
) : GetFoldersUseCase {

    override operator fun invoke() = repository.getFolders()
}