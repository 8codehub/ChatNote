package com.chatnote.coredata.di.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.chatnote.coredata.di.db.AppDatabase
import com.chatnote.coredata.di.db.FolderDao
import com.chatnote.coredata.di.db.MIGRATION_2_3
import com.chatnote.coredata.di.db.NoteDAO
import com.chatnote.coredata.di.db.NoteExtraDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {

        return try {
            Room.databaseBuilder(context, AppDatabase::class.java, "app_db")
                .addMigrations(MIGRATION_2_3)
                .build()
        } catch (e: Throwable) {
            Log.e("RoomMigration", "Migration failed: ${e.message}", e)
            e.printStackTrace()
            throw e // or handle accordingly
        }

    }

    @Provides
    fun provideFolderDao(database: AppDatabase): FolderDao = database.folderDao()

    @Provides
    fun provideNoteDao(database: AppDatabase): NoteDAO = database.noteDao()

    @Provides
    fun provideNoteExtraDao(database: AppDatabase): NoteExtraDao = database.noteExtraDao()

}