package com.sendme.data.repository.bridge

import com.pingpad.coredomain.bridge.NotesRepositoryFacade
import com.pingpad.coredomain.utils.ResultError
import com.pingpad.coredomain.utils.failure
import com.sendme.data.db.NoteDao
import javax.inject.Inject

class FolderNotesHandlerImpl @Inject constructor(
    private val noteDao: NoteDao
) : NotesRepositoryFacade {

    override suspend fun deleteNotesByFolderId(folderId: Long): Result<Int> {
        return try {
            Result.success(noteDao.deleteNotesByFolderId(folderId = folderId))
        } catch (e: Exception) {
            Result.failure(ResultError.DatabaseError(e))
        }
    }
}