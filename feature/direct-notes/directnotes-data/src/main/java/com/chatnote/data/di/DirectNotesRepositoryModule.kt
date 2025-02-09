package com.chatnote.data.di

import com.chatnote.coredata.di.db.NoteDao
import com.chatnote.coredata.di.model.NoteEntity
import com.chatnote.coredomain.facade.FolderRepositoryFacade
import com.chatnote.coredomain.facade.NotesRepositoryFacade
import com.chatnote.coredomain.mapper.Mapper
import com.chatnote.data.repository.NotesRepositoryImpl
import com.chatnote.data.repository.NotesStreamRepositoryImpl
import com.chatnote.data.repository.facade.FolderNotesFacadeImpl
import com.chatnote.directnotesdomain.model.Note
import com.chatnote.directnotesdomain.repository.NotesRepository
import com.chatnote.directnotesdomain.repository.NotesStreamRepository
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
        folderRepositoryFacade = folderUpdateHandler,
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
