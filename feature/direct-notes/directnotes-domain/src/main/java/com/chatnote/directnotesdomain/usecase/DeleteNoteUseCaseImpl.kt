package com.chatnote.directnotesdomain.usecase

import com.chatnote.coredomain.facade.FolderRepositoryFacade
import com.chatnote.directnotesdomain.repository.NotesRepository
import javax.inject.Inject

class DeleteNoteUseCaseImpl @Inject constructor(
    private val folderRepositoryFacade: FolderRepositoryFacade,
    private val notesRepository: NotesRepository
) : DeleteNoteUseCase {
    override suspend fun invoke(noteId: Long, folderId: Long): Result<Unit> {
        return folderRepositoryFacade.deleteFolderLastNoteIfMatch(
            folderId = folderId,
            noteId = noteId
        )
    }


}