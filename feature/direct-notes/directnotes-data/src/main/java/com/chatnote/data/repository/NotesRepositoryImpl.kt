package com.chatnote.data.repository

import com.chatnote.coredomain.facade.FolderRepositoryFacade
import com.chatnote.coredomain.mapper.Mapper
import com.chatnote.coredomain.utils.ResultError
import com.chatnote.coredomain.utils.throwAsAppException
import com.chatnote.data.db.NoteDao
import com.chatnote.data.model.NoteEntity
import com.chatnote.directnotesdomain.model.Note
import com.chatnote.directnotesdomain.repository.NotesRepository
import javax.inject.Inject

class NotesRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao,
    private val folderUpdateHandler: FolderRepositoryFacade,
    private val domainToEntityMapper: Mapper<Note, NoteEntity>
) : NotesRepository {

    override suspend fun addNote(folderId: Long, note: Note): Result<Unit> {
        return runCatching {
            val createdDate = System.currentTimeMillis()
            val noteEntity = domainToEntityMapper.map(note).copy(
                folderId = folderId,
                createdAt = createdDate
            )

            val insertedNoteId = noteDao.insertNote(noteEntity)
            if (insertedNoteId > 0) {
                folderUpdateHandler.updateFolderWithLastNote(
                    folderId = folderId,
                    lastNote = note.content,
                    lastNoteDate = createdDate
                )
            } else {
                ResultError.DatabaseError().throwAsAppException()
            }
        }.fold(
            onSuccess = {
                Result.success(Unit)
            },
            onFailure = {
                Result.failure(it)
            }
        )
    }
}
