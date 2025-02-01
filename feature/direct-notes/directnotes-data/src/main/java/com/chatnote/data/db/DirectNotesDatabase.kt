package com.chatnote.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chatnote.data.model.NoteEntity

@Database(entities = [NoteEntity::class], version = 1, exportSchema = false)
abstract class DirectNotesDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}
