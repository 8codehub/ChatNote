package com.chatnote.data.mapper

import com.chatnote.coredata.di.db.NoteWithExtras
import com.chatnote.coredata.di.model.NoteEntity
import com.chatnote.coredata.di.model.NoteEntityExtraType
import com.chatnote.coredata.di.model.NoteExtraEntity
import com.chatnote.coredomain.mapper.Mapper
import com.chatnote.coredomain.models.NoteExtra
import com.chatnote.coredomain.models.NoteExtraType
import com.chatnote.directnotesdomain.model.Note
import javax.inject.Inject

internal class NoteEntityToNoteMapper @Inject constructor() : Mapper<NoteEntity, Note> {

    override fun map(from: NoteEntity): Note = Note(
        id = from.id,
        folderId = from.folderId,
        content = from.content,
        createdAt = from.createdAt,
        extras = emptyList()
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

class NoteExtraEntityToNoteExtraMapper @Inject constructor() :
    Mapper<NoteExtraEntity, NoteExtra> {
    override fun map(from: NoteExtraEntity): NoteExtra {
        return NoteExtra(
            id = from.id,
            type = NoteExtraType.valueOf(from.type.name.uppercase()),
            value = from.value
        )
    }
}

class NoteExtraToNoteExtraEntityMapper @Inject constructor(
    private val mapper: Mapper<NoteExtraType, NoteEntityExtraType>
) :
    Mapper<NoteExtra, NoteExtraEntity> {
    override fun map(from: NoteExtra): NoteExtraEntity {
        return NoteExtraEntity(
            id = from.id,
            noteId = 0L,
            type = mapper.map(from = from.type),
            value = from.value
        )
    }
}

class NoteWithExtrasToNoteMapper @Inject constructor(
    private val extrasMapper: Mapper<NoteExtraEntity, NoteExtra>
) : Mapper<NoteWithExtras, Note> {
    override fun map(from: NoteWithExtras): Note {
        return Note(
            id = from.note.id,
            content = from.note.content,
            folderId = from.note.folderId,
            createdAt = from.note.createdAt,
            extras = extrasMapper.mapList(from.extras)
        )
    }
}
