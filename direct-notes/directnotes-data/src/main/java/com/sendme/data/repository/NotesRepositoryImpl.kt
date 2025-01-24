package com.sendme.data.repository

import com.pingpad.coredomain.navigation.bridge.FolderRepositoryFacade
import com.pingpad.coredomain.navigation.mapper.Mapper
import com.sendme.data.db.NoteDao
import com.sendme.data.model.NoteEntity
import com.sendme.directnotsdomain.SendMeNote
import com.sendme.directnotsdomain.repository.NotesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotesRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao,
    private val folderUpdateHandler: FolderRepositoryFacade,
    private val entityToDomainMapper: Mapper<NoteEntity, SendMeNote>,
    private val domainToEntityMapper: Mapper<SendMeNote, NoteEntity>
) : NotesRepository {

    override fun getNotes(folderId: Long): Flow<List<SendMeNote>> {
        return entityToDomainMapper.mapFlow(noteDao.getNotesForFolder(folderId))
    }

    override suspend fun addNote(folderId: Long, note: SendMeNote) {
        val createdDate = System.currentTimeMillis()
        val noteEntity =
            domainToEntityMapper.map(note).copy(folderId = folderId, createdAt = createdDate)
        noteDao.insertNote(noteEntity)
        // After inserting the note, update the folder
        folderUpdateHandler.updateFolderWithLastNote(
            folderId = folderId,
            lastNote = note.content,
            lastNoteDate = createdDate
        )
    }
}