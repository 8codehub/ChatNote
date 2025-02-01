package com.sendme.data.repository

import com.pingpad.coredomain.mapper.Mapper
import com.sendme.data.db.NoteDao
import com.sendme.data.model.NoteEntity
import com.sendme.directnotsdomain.model.Note
import com.sendme.directnotsdomain.repository.NotesStreamRepository
import javax.inject.Inject

class NotesStreamRepositoryImpl @Inject constructor(
    private val entityToDomainMapper: Mapper<NoteEntity, Note>,
    private val noteDao: NoteDao,
) : NotesStreamRepository {
    override fun observeNotes(folderId: Long) =
        entityToDomainMapper.mapFlow(noteDao.getNotesForFolder(folderId))

}