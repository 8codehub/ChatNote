package com.chatnote.data.repository

import com.chatnote.coredata.di.db.NoteDAO
import com.chatnote.coredata.di.db.NoteWithExtras
import com.chatnote.coredomain.mapper.Mapper
import com.chatnote.directnotesdomain.model.Note
import com.chatnote.directnotesdomain.repository.NotesStreamRepository
import javax.inject.Inject

internal class NotesStreamRepositoryImpl @Inject constructor(
    private val noteEntityToNoteDomainMapper: Mapper<NoteWithExtras, Note>,
    private val noteDao: NoteDAO,
) : NotesStreamRepository {

    override fun observeNotes(folderId: Long) =
        noteEntityToNoteDomainMapper.mapFlow(noteDao.getNotesWithExtrasForFolder(folderId))
}