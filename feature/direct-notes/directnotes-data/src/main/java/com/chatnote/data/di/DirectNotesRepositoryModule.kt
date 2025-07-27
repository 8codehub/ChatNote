package com.chatnote.data.di

import com.chatnote.coredata.di.db.NoteDAO
import com.chatnote.coredata.di.db.NoteExtraDao
import com.chatnote.coredata.di.db.NoteWithExtras
import com.chatnote.coredata.di.model.NoteEntity
import com.chatnote.coredata.di.model.NoteExtraEntity
import com.chatnote.coredomain.facade.NotesRepositoryFacade
import com.chatnote.coredomain.mapper.Mapper
import com.chatnote.coredomain.manager.FileManager
import com.chatnote.data.repository.NotesRepositoryImpl
import com.chatnote.data.repository.NotesStreamRepositoryImpl
import com.chatnote.data.repository.facade.FolderNotesFacadeImpl
import com.chatnote.directnotesdomain.model.Note
import com.chatnote.coredomain.models.NoteExtra
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
        noteDao: NoteDAO,
        noteExtraDao: NoteExtraDao,
        fileManager: FileManager,
        domainNoteToNoteEntityMapper: Mapper<Note, NoteEntity>,
        noteEntityToNoteDomainMapper: Mapper<NoteEntity, Note>,
        noteExtraToNoteExtraEntityMapper: Mapper<NoteExtra, NoteExtraEntity>

    ): NotesRepository = NotesRepositoryImpl(
        noteDao = noteDao,
        noteExtraDao = noteExtraDao,
        fileManager = fileManager,
        domainNoteToNoteEntityMapper = domainNoteToNoteEntityMapper,
        noteEntityToNoteDomainMapper = noteEntityToNoteDomainMapper,
        noteExtraToNoteExtraEntityMapper = noteExtraToNoteExtraEntityMapper
    )

    @Provides
    @Singleton
    fun provideNotesStreamRepository(
        noteDao: NoteDAO,
        noteEntityToNoteDomainMapper: Mapper<NoteWithExtras, Note>
    ): NotesStreamRepository = NotesStreamRepositoryImpl(
        noteDao = noteDao,
        noteEntityToNoteDomainMapper = noteEntityToNoteDomainMapper,
    )

    @Provides
    @Singleton
    fun provideFolderNotesHandler(
        noteDao: NoteDAO,
        fileManager: FileManager
    ): NotesRepositoryFacade = FolderNotesFacadeImpl(
        noteDao = noteDao,
        fileManager = fileManager
    )
}
