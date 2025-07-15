package com.chatnote.coredata.di.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.chatnote.coredata.di.db.convertor.Converters
import com.chatnote.coredata.di.model.FolderEntity
import com.chatnote.coredata.di.model.NoteEntity

@Database(
    entities = [FolderEntity::class, NoteEntity::class],
    version = 3,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun folderDao(): FolderDao
    abstract fun noteDao(): NoteDAO
}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE notes ADD COLUMN note_image_path TEXT")
    }
}
