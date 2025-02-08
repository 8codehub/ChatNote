package com.chatnote.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "folders")
data class FolderEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val name: String,
    val iconUri: String?,
    val lastNoteContent: String?,
    val lastNoteId: Long?,
    val lastNoteCreatedAt: Long?,
    val createdAt: Long = System.currentTimeMillis(),
    val pinnedDate: Long = 0 //0 for unpinned folders
)