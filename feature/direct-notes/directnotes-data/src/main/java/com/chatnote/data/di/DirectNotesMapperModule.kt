package com.chatnote.data.di

import com.chatnote.coredata.di.model.NoteEntity
import com.chatnote.coredomain.mapper.Mapper
import com.chatnote.data.mapper.NoteEntityToNoteMapper
import com.chatnote.data.mapper.NoteToNoteEntityMapper
import com.chatnote.directnotesdomain.model.Note
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DirectNotesMapperModule {

    @Provides
    @Singleton
    fun provideNoteEntityToNoteMapper(): Mapper<NoteEntity, Note> =
        NoteEntityToNoteMapper()

    @Provides
    @Singleton
    fun provideNoteToNoteEntityMapper(): Mapper<Note, NoteEntity> =
        NoteToNoteEntityMapper()
}
