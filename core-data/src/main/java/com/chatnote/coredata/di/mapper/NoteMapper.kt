package com.chatnote.coredata.di.mapper

import com.chatnote.coredata.di.model.NoteEntityExtraType
import com.chatnote.coredomain.mapper.Mapper
import com.chatnote.coredomain.models.NoteExtraType
import javax.inject.Inject

class NoteExtraTypeToNoteEntityExtraTypeMapper @Inject constructor() :
    Mapper<NoteExtraType, NoteEntityExtraType> {
    override fun map(from: NoteExtraType): NoteEntityExtraType {
        return when (from) {
            NoteExtraType.IMAGE -> NoteEntityExtraType.IMAGE
            NoteExtraType.VOICE -> NoteEntityExtraType.VOICE
            NoteExtraType.FILE -> NoteEntityExtraType.FILE
        }
    }
}


class NoteEntityExtraTypeToNoteExtraTypeMapper @Inject constructor() :
    Mapper<NoteEntityExtraType, NoteExtraType> {
    override fun map(from: NoteEntityExtraType): NoteExtraType {
        return when (from) {
            NoteEntityExtraType.IMAGE -> NoteExtraType.IMAGE
            NoteEntityExtraType.VOICE -> NoteExtraType.VOICE
            NoteEntityExtraType.FILE -> NoteExtraType.FILE
        }
    }
}