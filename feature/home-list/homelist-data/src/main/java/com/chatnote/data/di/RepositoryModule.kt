package com.chatnote.data.di

import com.chatnote.coredata.di.db.FolderDao
import com.chatnote.coredata.di.model.FolderEntity
import com.chatnote.coredata.di.model.FolderWithLastNote
import com.chatnote.coredomain.facade.FolderRepositoryFacade
import com.chatnote.coredomain.facade.FolderStreamRepositoryFacade
import com.chatnote.coredomain.facade.NotesRepositoryFacade
import com.chatnote.coredomain.mapper.Mapper
import com.chatnote.coredomain.models.FolderBaseInfo
import com.chatnote.data.repository.FolderRepositoryImpl
import com.chatnote.data.repository.FolderStreamRepositoryImpl
import com.chatnote.data.repository.facade.FolderRepositoryFacadeImpl
import com.chatnote.data.repository.facade.FolderStreamRepositoryFacadeImpl
import com.chatnote.domain.model.Folder
import com.chatnote.domain.repository.FolderRepository
import com.chatnote.domain.repository.FolderStreamRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideFolderRepository(
        folderDao: FolderDao,
        mapperFolderEntityToFolder: Mapper<FolderEntity, Folder>
    ): FolderRepository = FolderRepositoryImpl(
        folderDao = folderDao,
        mapperFolderEntityToFolder = mapperFolderEntityToFolder
    )

    @Provides
    @Singleton
    fun provideFolderStreamRepository(
        folderDao: FolderDao,
        notesRepositoryFacade: NotesRepositoryFacade,
        folderWithLastNoteToFolder: Mapper<FolderWithLastNote, Folder>
    ): FolderStreamRepository = FolderStreamRepositoryImpl(
        folderDao = folderDao,
        notesRepositoryFacade = notesRepositoryFacade,
        folderWithLastNoteToFolder = folderWithLastNoteToFolder
    )

    @Provides
    @Singleton
    fun provideFolderRepositoryFacade(
        folderDao: FolderDao,
    ): FolderRepositoryFacade = FolderRepositoryFacadeImpl(
        folderDao = folderDao,
    )

    @Provides
    @Singleton
    fun provideFolderStreamRepositoryFacade(
        folderDao: FolderDao,
        mapperFolderEntityToFolderBaseInfo: Mapper<FolderEntity, FolderBaseInfo>
    ): FolderStreamRepositoryFacade = FolderStreamRepositoryFacadeImpl(
        folderDao = folderDao,
        mapperFolderEntityToFolderBaseInfo = mapperFolderEntityToFolderBaseInfo
    )
}
