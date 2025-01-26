package com.sendme.directnotsdomain.repository

import com.sendme.directnotsdomain.SendMeNote

interface NotesRepository {

    suspend fun addNote(folderId: Long, note: SendMeNote): Result<Unit>
}
