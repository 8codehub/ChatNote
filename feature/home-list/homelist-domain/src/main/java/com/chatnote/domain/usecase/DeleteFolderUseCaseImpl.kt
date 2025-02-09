package com.chatnote.domain.usecase

import com.chatnote.coredomain.facade.NotesRepositoryFacade
import com.chatnote.coredomain.utils.ResultError
import com.chatnote.coredomain.utils.failure
import com.chatnote.domain.repository.FolderRepository
import javax.inject.Inject

internal class DeleteFolderUseCaseImpl @Inject constructor(
    private val repository: FolderRepository,
    private val folderNotesHandler: NotesRepositoryFacade
) : DeleteFolderUseCase {

    override suspend fun invoke(folderId: Long) = repository.deleteFolder(folderId).fold(
        onSuccess = {
            folderNotesHandler.deleteNotesByFolderId(folderId)
        },
        onFailure = { throwable ->
            Result.failure(ResultError.DeleteFolderFail(throwable))
        }
    )
}
