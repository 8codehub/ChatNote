package com.sendme.data.di

import com.pingpad.coredomain.mapper.Mapper
import com.sendme.data.mapper.NoteEntityToSendMeNoteMapper
import com.sendme.data.mapper.SendMeNoteToNoteEntityMapper
import com.sendme.data.model.NoteEntity
import com.sendme.directnotsdomain.SendMeNote
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
    fun provideNoteEntityToSendMeNoteMapper(): Mapper<NoteEntity, SendMeNote> =
        NoteEntityToSendMeNoteMapper()

    @Provides
    @Singleton
    fun provideSendMeNoteToNoteEntityMapper(): Mapper<SendMeNote, NoteEntity> =
        SendMeNoteToNoteEntityMapper()
}
