package com.sendme.data.di

import com.pingpad.coredomain.mapper.Mapper
import com.sendme.data.mapper.NoteEntityToSendMeNoteMapper
import com.sendme.data.mapper.SendMeNoteToNoteEntityMapper
import com.sendme.data.model.NoteEntity
import com.sendme.directnotsdomain.model.Note
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
    fun provideNoteEntityToSendMeNoteMapper(): Mapper<NoteEntity, Note> =
        NoteEntityToSendMeNoteMapper()

    @Provides
    @Singleton
    fun provideSendMeNoteToNoteEntityMapper(): Mapper<Note, NoteEntity> =
        SendMeNoteToNoteEntityMapper()
}
