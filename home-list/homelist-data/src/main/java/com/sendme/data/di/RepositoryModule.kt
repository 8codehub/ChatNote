package com.sendme.data.di

import com.pingpad.coredomain.facade.FolderRepositoryFacade
import com.pingpad.coredomain.facade.FolderStreamRepositoryFacade
import com.pingpad.coredomain.mapper.Mapper
import com.pingpad.coredomain.models.FolderBaseInfo
import com.sendme.data.db.FolderDao
import com.sendme.data.models.FolderEntity
import com.sendme.data.repository.FolderRepositoryImpl
import com.sendme.data.repository.FolderStreamRepositoryImpl
import com.sendme.data.repository.facade.FolderRepositoryFacadeImpl
import com.sendme.data.repository.facade.FolderStreamRepositoryFacadeImpl
import com.sendme.domain.model.Folder
import com.sendme.domain.repository.FolderRepository
import com.sendme.domain.repository.FolderStreamRepository
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
        mapperFolderEntityToFolder: Mapper<FolderEntity, Folder>
    ): FolderStreamRepository = FolderStreamRepositoryImpl(
        folderDao = folderDao,
        mapperFolderEntityToFolder = mapperFolderEntityToFolder
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
