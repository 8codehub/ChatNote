package com.chatnote.data.repository

import com.chatnote.coredomain.mapper.Mapper
import com.chatnote.data.db.NoteDao
import com.chatnote.data.model.NoteEntity
import com.chatnote.directnotesdomain.model.Note
import com.chatnote.directnotesdomain.repository.NotesStreamRepository
import javax.inject.Inject

class NotesStreamRepositoryImpl @Inject constructor(
    private val entityToDomainMapper: Mapper<NoteEntity, Note>,
    private val noteDao: NoteDao,
) : NotesStreamRepository {
    override fun observeNotes(folderId: Long) =
        entityToDomainMapper.mapFlow(noteDao.getNotesForFolder(folderId))

}