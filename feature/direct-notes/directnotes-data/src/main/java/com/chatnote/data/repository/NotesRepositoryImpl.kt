package com.chatnote.data.repository

import com.chatnote.coredata.di.db.NoteDao
import com.chatnote.coredata.di.model.NoteEntity
import com.chatnote.coredomain.mapper.Mapper
import com.chatnote.coredomain.utils.ResultError
import com.chatnote.coredomain.utils.throwAsAppException
import com.chatnote.directnotesdomain.model.Note
import com.chatnote.directnotesdomain.repository.NotesRepository
import javax.inject.Inject

internal class NotesRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao,
    private val domainNoteToNoteEntityMapper: Mapper<Note, NoteEntity>,
    private val noteEntityToNoteDomainMapper: Mapper<NoteEntity, Note>
) : NotesRepository {

    override suspend fun addNote(folderId: Long, note: Note): Result<Unit> {
        return runCatching {
            val createdDate = System.currentTimeMillis()
            val noteEntity = domainNoteToNoteEntityMapper.map(note).copy(
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

    override suspend fun getNoteById(noteId: Long): Result<Note> {
        return runCatching {
            noteDao.getNoteById(noteId = noteId)
        }.fold(onSuccess = { entity ->
            entity?.let {
                Result.success(noteEntityToNoteDomainMapper.map(entity))
            } ?: ResultError.NoteNotFound.throwAsAppException()
        }, onFailure = { Result.failure(it) }
        )
    }

    override suspend fun updateNoteContent(noteId: Long, content: String): Result<Unit> {
        return runCatching {
            noteDao.updateNoteContentById(noteId = noteId, content = content)
        }.fold(onSuccess = {
            Result.success(Unit)
        }, onFailure = { Result.failure(it) }
        )
    }
}
