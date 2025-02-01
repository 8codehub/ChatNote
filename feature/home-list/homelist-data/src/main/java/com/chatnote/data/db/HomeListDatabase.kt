package com.chatnote.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chatnote.data.models.FolderEntity

@Database(
    entities = [FolderEntity::class],
    version = 1,
    exportSchema = false
)
abstract class HomeListDatabase : RoomDatabase() {
    abstract fun folderDao(): FolderDao
}
