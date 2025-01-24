package com.sendme.data.repository.bridge

import com.pingpad.coredomain.navigation.bridge.NotesRepositoryFacade
import com.sendme.data.db.NoteDao
import javax.inject.Inject

class FolderNotesHandlerImpl @Inject constructor(private val noteDao: NoteDao) :
    NotesRepositoryFacade {
    override suspend fun deleteNotesByFolderId(folderId: Long): Result<Int> {
        val deletedNotesCount = noteDao.deleteNotesByFolderId(folderId = folderId)
        return if (deletedNotesCount > 0) {
            Result.success(deletedNotesCount)
        } else {
            Result.failure(Throwable("No notes found to delete for folder ID: $folderId"))
        }
    }

}