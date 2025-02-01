package com.chatnote.data.mapper

import com.chatnote.coredomain.mapper.Mapper
import com.chatnote.data.model.NoteEntity
import com.chatnote.directnotesdomain.model.Note
import javax.inject.Inject

internal class NoteEntityToNoteMapper @Inject constructor() : Mapper<NoteEntity, Note> {

    override fun map(from: NoteEntity): Note = Note(
        id = from.id,
        folderId = from.folderId,
        content = from.content,
        createdAt = from.createdAt
    )
}

internal class NoteToNoteEntityMapper @Inject constructor() : Mapper<Note, NoteEntity> {

    override fun map(from: Note): NoteEntity = NoteEntity(
        id = from.id,
        content = from.content,
        createdAt = System.currentTimeMillis(),
        folderId = 0
    )
}
