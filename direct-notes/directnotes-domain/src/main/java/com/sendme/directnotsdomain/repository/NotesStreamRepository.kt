package com.sendme.directnotsdomain.repository

import com.sendme.directnotsdomain.SendMeNote
import kotlinx.coroutines.flow.Flow

interface NotesStreamRepository {
    fun observeNotes(folderId: Long): Flow<List<SendMeNote>>
}