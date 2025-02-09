package com.chatnote.coredata.di.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chatnote.coredata.di.model.FolderEntity
import com.chatnote.coredata.di.model.NoteEntity

@Database(
    entities = [FolderEntity::class, NoteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun folderDao(): FolderDao
    abstract fun noteDao(): NoteDao
}
