package com.sendme.domain.usecase

import com.pingpad.coredomain.bridge.NotesRepositoryFacade
import com.pingpad.coredomain.utils.ResultError
import com.pingpad.coredomain.utils.failure
import com.sendme.domain.repository.FolderRepository
import javax.inject.Inject

internal class DeleteFolderUseCaseImpl @Inject constructor(
    private val repository: FolderRepository,
    private val folderNotesHandler: NotesRepositoryFacade
) : DeleteFolderUseCase {

    override suspend fun invoke(folderId: Long): Result<Int> {
        val result = repository.deleteFolder(folderId = folderId)

        return result.fold(
            onSuccess = {
                folderNotesHandler.deleteNotesByFolderId(folderId = folderId)
            },
            onFailure = { throwable ->
                Result.failure(ResultError.DeleteFolderFail(throwable))
            }
        )
    }
}
