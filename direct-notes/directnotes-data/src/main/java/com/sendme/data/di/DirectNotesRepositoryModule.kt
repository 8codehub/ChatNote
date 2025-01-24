package com.sendme.data.di

import com.pingpad.coredomain.navigation.bridge.FolderNotesHandler
import com.pingpad.coredomain.navigation.bridge.FolderUpdateHandler
import com.pingpad.coredomain.navigation.mapper.Mapper
import com.sendme.data.db.NoteDao
import com.sendme.data.model.NoteEntity
import com.sendme.data.repository.NotesRepositoryImpl
import com.sendme.data.repository.bridge.FolderNotesHandlerImpl
import com.sendme.directnotsdomain.SendMeNote
import com.sendme.directnotsdomain.repository.NotesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DirectNotesRepositoryModule {
    @Provides
    @Singleton
    fun provideNotesRepository(
        noteDao: NoteDao,
        folderUpdateHandler: FolderUpdateHandler,
        entityToDomainMapper: Mapper<NoteEntity, SendMeNote>,
        domainToEntityMapper: Mapper<SendMeNote, NoteEntity>
    ): NotesRepository {
        return NotesRepositoryImpl(
            noteDao = noteDao,
            folderUpdateHandler = folderUpdateHandler,
            entityToDomainMapper = entityToDomainMapper,
            domainToEntityMapper = domainToEntityMapper
        )
    }

    @Provides
    @Singleton
    fun provideFolderNotesHandler(
        noteDao: NoteDao
    ): FolderNotesHandler {
        return FolderNotesHandlerImpl(
            noteDao = noteDao
        )
    }
}