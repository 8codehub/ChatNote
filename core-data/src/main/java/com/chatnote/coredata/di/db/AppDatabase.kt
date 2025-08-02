package com.chatnote.coredata.di.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.chatnote.coredata.di.db.convertor.NoteExtraTypeConverter
import com.chatnote.coredata.di.model.FolderEntity
import com.chatnote.coredata.di.model.NoteEntity
import com.chatnote.coredata.di.model.NoteExtraEntity

@Database(
    entities = [FolderEntity::class, NoteEntity::class, NoteExtraEntity::class],
    version = 3,
    exportSchema = false
)
@TypeConverters(NoteExtraTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun folderDao(): FolderDao
    abstract fun noteDao(): NoteDAO
    abstract fun noteExtraDao(): NoteExtraDao
}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS note_extras (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                note_id INTEGER NOT NULL,
                type INTEGER NOT NULL,
                value TEXT NOT NULL,
                FOREIGN KEY(note_id) REFERENCES notes(id) ON DELETE CASCADE
            )
            """.trimIndent()
        )

        db.execSQL("CREATE INDEX IF NOT EXISTS index_note_extras_note_id ON note_extras(note_id)")
    }
}
