package com.sendme.directnotsdomain.repository

import com.sendme.directnotsdomain.SendMeNote
import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    fun getNotes(folderId: Long): Flow<List<SendMeNote>>
    suspend fun addNote(folderId: Long, note: SendMeNote)
}