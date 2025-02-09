package com.chatnote.coredata.di.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "folders")
data class FolderEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val name: String,
    val iconUri: String?,
    val createdAt: Long = System.currentTimeMillis(),
    val pinnedDate: Long = 0
)