package com.sendme.data.di

import com.pingpad.coredomain.navigation.bridge.FolderRepositoryFacade
import com.pingpad.coredomain.navigation.mapper.Mapper
import com.pingpad.coredomain.navigation.models.FolderBaseInfo
import com.sendme.data.db.FolderDao
import com.sendme.data.models.FolderEntity
import com.sendme.data.repository.FolderRepositoryImpl
import com.sendme.data.repository.bridge.FolderRepositoryFacadeImpl
import com.sendme.domain.model.Folder
import com.sendme.domain.repository.FolderRepository
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
        folderDao: FolderDao, // Inject FolderDao provided from DatabaseModule
        mapperFolderToFolderEntity: Mapper<Folder, FolderEntity>, // Inject mapper
        mapperFolderEntityToFolder: Mapper<FolderEntity, Folder> // Inject mapper
    ): FolderRepository {
        return FolderRepositoryImpl(
            folderDao = folderDao,
            mapperFolderToFolderEntity = mapperFolderToFolderEntity,
            mapperFolderEntityToFolder = mapperFolderEntityToFolder
        )
    }

    @Provides
    @Singleton
    fun provideFolderUpdateHandler(
        folderDao: FolderDao,
        mapperFolderEntityToFolderBaseInfo: Mapper<FolderEntity, FolderBaseInfo>
    ): FolderRepositoryFacade {
        return FolderRepositoryFacadeImpl(
            folderDao = folderDao,
            mapperFolderEntityToFolderBaseInfo = mapperFolderEntityToFolderBaseInfo
        )
    }
}