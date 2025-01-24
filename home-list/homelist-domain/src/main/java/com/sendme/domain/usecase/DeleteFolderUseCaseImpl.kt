package com.sendme.domain.usecase

import com.pingpad.coredomain.navigation.bridge.NotesRepositoryFacade
import com.sendme.domain.repository.FolderRepository
import javax.inject.Inject

internal class DeleteFolderUseCaseImpl @Inject constructor(
    private val repository: FolderRepository,
    private val folderNotesHandler: NotesRepositoryFacade
) : DeleteFolderUseCase {

    override suspend fun invoke(folderId: Long): Result<Int> {
        val result = repository.deleteFolder(folderId = folderId)
        if (result.isSuccess) {
            return folderNotesHandler.deleteNotesByFolderId(folderId = folderId)
        }
        return Result.failure(result.exceptionOrNull() ?: Throwable("Something went wrong"))
    }
}
