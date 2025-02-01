package com.chatnote.directnotesdomain.repository

import com.chatnote.directnotesdomain.model.Note
import kotlinx.coroutines.flow.Flow

interface NotesStreamRepository {
    fun observeNotes(folderId: Long): Flow<List<Note>>
}