package com.sendme.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sendme.data.models.FolderEntity

@Database(
    entities = [FolderEntity::class],
    version = 1,
    exportSchema = false
)
abstract class HomeListDatabase : RoomDatabase() {
    abstract fun folderDao(): FolderDao
}
