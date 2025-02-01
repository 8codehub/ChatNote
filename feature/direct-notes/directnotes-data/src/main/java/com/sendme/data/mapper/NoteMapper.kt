package com.sendme.data.mapper

import com.pingpad.coredomain.mapper.Mapper
import com.sendme.data.model.NoteEntity
import com.sendme.directnotsdomain.model.Note
import javax.inject.Inject

internal class NoteEntityToSendMeNoteMapper @Inject constructor() : Mapper<NoteEntity, Note> {

    override fun map(from: NoteEntity): Note = Note(
        id = from.id,
        folderId = from.folderId,
        content = from.content,
        createdAt = from.createdAt
    )
}

internal class SendMeNoteToNoteEntityMapper @Inject constructor() : Mapper<Note, NoteEntity> {

    override fun map(from: Note): NoteEntity = NoteEntity(
        id = from.id,
        content = from.content,
        createdAt = System.currentTimeMillis(),
        folderId = 0
    )
}
