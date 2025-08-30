package com.chatnote.coredata.di.db

import androidx.room.Embedded
import androidx.room.Relation
import com.chatnote.coredata.di.model.NoteEntity
import com.chatnote.coredata.di.model.NoteExtraEntity

data class NoteWithExtras(
    @Embedded val note: NoteEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "note_id"
    )
    val extras: List<NoteExtraEntity>
)