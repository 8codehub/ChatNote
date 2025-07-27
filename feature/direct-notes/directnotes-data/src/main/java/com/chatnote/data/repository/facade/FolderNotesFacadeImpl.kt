package com.chatnote.data.repository.facade

import com.chatnote.coredata.di.db.NoteDAO
import com.chatnote.coredomain.facade.NotesRepositoryFacade
import com.chatnote.coredomain.manager.FileManager
import com.chatnote.coredomain.utils.ResultError
import com.chatnote.coredomain.utils.failure
import javax.inject.Inject

internal class FolderNotesFacadeImpl @Inject constructor(
    private val noteDao: NoteDAO,
    private val fileManager: FileManager
) : NotesRepositoryFacade {

    override suspend fun deleteNotesByFolderId(folderId: Long): Result<Int> {
        return runCatching {
            val notesWithExtras = noteDao.getNotesWithExtrasForFolderStatic(folderId) // Step 1

            // Step 2 & 3: Delete all extra files
            notesWithExtras.flatMap { it.extras }.forEach { extra ->
                fileManager.deleteFileIfExist(extra.value)
            }

            // Step 4: Delete notes (cascades to extras)
            noteDao.deleteNotesByFolderId(folderId)
        }.fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(ResultError.DatabaseError(it)) }
        )
    }
}
