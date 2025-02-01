package com.chatnote.data.di

import android.content.Context
import androidx.room.Room
import com.chatnote.data.db.DirectNotesDatabase
import com.chatnote.data.db.NoteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DirectNotesDatabaseModule {

    @Provides
    @Singleton
    fun provideDirectNotesDatabase(
        @ApplicationContext context: Context
    ): DirectNotesDatabase {
        return Room.databaseBuilder(
            context,
            DirectNotesDatabase::class.java,
            "direct_notes_database"
        ).build()
    }

    @Provides
    fun provideNoteDao(
        database: DirectNotesDatabase
    ): NoteDao = database.noteDao()
}
