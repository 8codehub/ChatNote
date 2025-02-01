package com.chatnote.domain.usecase

import com.chatnote.domain.repository.FolderRepository
import javax.inject.Inject

internal class AddOrUpdateFolderUseCaseImpl @Inject constructor(
    private val repository: FolderRepository
) : AddOrUpdateFolderUseCase {

    override suspend operator fun invoke(
        folderId: Long?,
        name: String,
        iconUri: String
    ) = repository.addOrUpdateFolder(
        folderId = folderId,
        name = name,
        iconUri = iconUri
    )
}
