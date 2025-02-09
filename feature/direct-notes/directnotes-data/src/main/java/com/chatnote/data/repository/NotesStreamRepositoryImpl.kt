package com.chatnote.data.repository

import com.chatnote.coredata.di.db.NoteDao
import com.chatnote.coredata.di.model.NoteEntity
import com.chatnote.coredomain.mapper.Mapper
import com.chatnote.directnotesdomain.model.Note
import com.chatnote.directnotesdomain.repository.NotesStreamRepository
import javax.inject.Inject

internal class NotesStreamRepositoryImpl @Inject constructor(
    private val entityToDomainMapper: Mapper<NoteEntity, Note>,
    private val noteDao: NoteDao,
) : NotesStreamRepository {

    override fun observeNotes(folderId: Long) =
        entityToDomainMapper.mapFlow(noteDao.getNotesForFolder(folderId))
}