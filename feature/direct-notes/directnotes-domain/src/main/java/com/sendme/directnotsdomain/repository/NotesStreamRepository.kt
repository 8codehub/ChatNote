package com.sendme.directnotsdomain.repository

import com.sendme.directnotsdomain.model.Note
import kotlinx.coroutines.flow.Flow

interface NotesStreamRepository {
    fun observeNotes(folderId: Long): Flow<List<Note>>
}