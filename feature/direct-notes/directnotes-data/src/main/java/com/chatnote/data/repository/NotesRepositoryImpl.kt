package com.chatnote.data.repository

import com.chatnote.coredata.di.db.NoteDao
import com.chatnote.coredata.di.model.NoteEntity
import com.chatnote.coredomain.mapper.Mapper
import com.chatnote.directnotesdomain.model.Note
import com.chatnote.directnotesdomain.repository.NotesRepository
import javax.inject.Inject

class NotesRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao,
    private val domainToEntityMapper: Mapper<Note, NoteEntity>
) : NotesRepository {

    override suspend fun addNote(folderId: Long, note: Note): Result<Unit> {
        return runCatching {
            val createdDate = System.currentTimeMillis()
            val noteEntity = domainToEntityMapper.map(note).copy(
                folderId = folderId,
                createdAt = createdDate
            )
            noteDao.insertNote(noteEntity)

        }.fold(
            onSuccess = {
                Result.success(Unit)
            },
            onFailure = {
                Result.failure(it)
            }
        )
    }

    override suspend fun deleteNote(noteId: Long): Result<Unit> {
        return runCatching {
            noteDao.deleteNoteById(noteId = noteId)
        }.fold(onSuccess = {
            Result.success(Unit)
        }, onFailure = { Result.failure(it) }
        )
    }
}
