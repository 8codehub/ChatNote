package com.chatnote.coredata.di.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val folderId: Long,
    @ColumnInfo(name = "note_last_content")
    val content: String,
    @ColumnInfo(name = "note_last_created_at")
    val createdAt: Long = System.currentTimeMillis()
)
