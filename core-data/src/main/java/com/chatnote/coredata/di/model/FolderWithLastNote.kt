package com.chatnote.coredata.di.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Relation

data class FolderWithLastNote(
    @Embedded val folder: FolderEntity,

    @ColumnInfo(name = "note_last_content")
    val lastNoteContent: String?,

    @ColumnInfo(name = "note_last_created_at")
    val lastNoteCreatedAt: Long?,

    @ColumnInfo(name = "note_last_id")
    val lastNoteId: Long?,

    @Relation(
        entity = NoteExtraEntity::class,
        parentColumn = "note_last_id",
        entityColumn = "note_id"
    )
    val extras: List<NoteExtraEntity>? = emptyList()
)