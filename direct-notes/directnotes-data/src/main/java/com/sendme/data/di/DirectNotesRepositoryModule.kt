package com.sendme.data.di

import com.pingpad.coredomain.facade.FolderRepositoryFacade
import com.pingpad.coredomain.facade.NotesRepositoryFacade
import com.pingpad.coredomain.mapper.Mapper
import com.sendme.data.db.NoteDao
import com.sendme.data.model.NoteEntity
import com.sendme.data.repository.NotesRepositoryImpl
import com.sendme.data.repository.NotesStreamRepositoryImpl
import com.sendme.data.repository.facade.FolderNotesFacadeImpl
import com.sendme.directnotsdomain.model.Note
import com.sendme.directnotsdomain.repository.NotesRepository
import com.sendme.directnotsdomain.repository.NotesStreamRepository
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
        folderUpdateHandler: FolderRepositoryFacade,
        entityToDomainMapper: Mapper<NoteEntity, Note>,
        domainToEntityMapper: Mapper<Note, NoteEntity>
    ): NotesRepository = NotesRepositoryImpl(
        noteDao = noteDao,
        folderUpdateHandler = folderUpdateHandler,
        domainToEntityMapper = domainToEntityMapper
    )

    @Provides
    @Singleton
    fun provideNotesStreamRepository(
        noteDao: NoteDao,
        entityToDomainMapper: Mapper<NoteEntity, Note>,
    ): NotesStreamRepository = NotesStreamRepositoryImpl(
        noteDao = noteDao,
        entityToDomainMapper = entityToDomainMapper,
    )

    @Provides
    @Singleton
    fun provideFolderNotesHandler(
        noteDao: NoteDao
    ): NotesRepositoryFacade = FolderNotesFacadeImpl(
        noteDao = noteDao
    )
}
