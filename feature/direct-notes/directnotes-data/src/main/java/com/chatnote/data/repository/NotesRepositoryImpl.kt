package com.chatnote.data.repository

import com.chatnote.coredata.di.db.NoteDAO
import com.chatnote.coredata.di.db.NoteExtraDao
import com.chatnote.coredata.di.model.NoteEntity
import com.chatnote.coredata.di.model.NoteExtraEntity
import com.chatnote.coredomain.manager.FileManager
import com.chatnote.coredomain.mapper.Mapper
import com.chatnote.coredomain.models.NoteExtra
import com.chatnote.coredomain.utils.ResultError
import com.chatnote.coredomain.utils.throwAsAppException
import com.chatnote.directnotesdomain.model.Note
import com.chatnote.directnotesdomain.repository.NotesRepository
import javax.inject.Inject

internal class NotesRepositoryImpl @Inject constructor(
    private val noteDao: NoteDAO,
    private val noteExtraDao: NoteExtraDao,
    private val fileManager: FileManager,
    private val domainNoteToNoteEntityMapper: Mapper<Note, NoteEntity>,
    private val noteEntityToNoteDomainMapper: Mapper<NoteEntity, Note>,
    private val noteExtraToNoteExtraEntityMapper: Mapper<NoteExtra, NoteExtraEntity>
) : NotesRepository {

    override suspend fun addNote(folderId: Long, note: Note): Result<Unit> {
        return runCatching {
            val createdDate = System.currentTimeMillis()
            val noteEntity = domainNoteToNoteEntityMapper.map(note).copy(
                folderId = folderId,
                createdAt = createdDate
            )
            val noteId = noteDao.insertNote(noteEntity)

            val extras = note.extras.map {
                noteExtraToNoteExtraEntityMapper.map(it).copy(noteId = noteId)
            }
            noteExtraDao.insertExtras(extras)
        }.fold(
            onSuccess = { Result.success(Unit) },
            onFailure = { Result.failure(it) }
        )
    }

    override suspend fun deleteNote(noteId: Long): Result<Unit> {
        val notExtras = noteDao.getNoteWithExtrasById(noteId)?.extras
        notExtras?.forEach {
            fileManager.deleteFileIfExist(it.value)
        }
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
