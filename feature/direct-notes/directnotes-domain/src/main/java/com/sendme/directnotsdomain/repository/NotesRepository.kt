package com.sendme.directnotsdomain.repository

import com.sendme.directnotsdomain.model.Note

interface NotesRepository {

    suspend fun addNote(folderId: Long, note: Note): Result<Unit>
}
