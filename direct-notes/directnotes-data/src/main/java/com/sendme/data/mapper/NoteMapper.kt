package com.sendme.data.mapper

import com.pingpad.coredomain.navigation.mapper.Mapper
import com.sendme.data.model.NoteEntity
import com.sendme.directnotsdomain.SendMeNote
import javax.inject.Inject

internal class NoteEntityToSendMeNoteMapper @Inject constructor() : Mapper<NoteEntity, SendMeNote> {
    override fun map(from: NoteEntity): SendMeNote {
        return SendMeNote(
            id = from.id,
            content = from.content
        )
    }
}

internal class SendMeNoteToNoteEntityMapper @Inject constructor() : Mapper<SendMeNote, NoteEntity> {
    override fun map(from: SendMeNote): NoteEntity {
        return NoteEntity(
            id = from.id,
            content = from.content,
            createdAt = System.currentTimeMillis(),
            folderId = 0 // Placeholder for folderId, must be provided dynamically
        )
    }
}