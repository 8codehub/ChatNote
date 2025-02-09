package com.chatnote.directnotesdomain.repository

import com.chatnote.directnotesdomain.model.Note

interface NotesRepository {

    suspend fun addNote(folderId: Long, note: Note): Result<Unit>
    suspend fun deleteNote(noteId: Long): Result<Unit>
}
