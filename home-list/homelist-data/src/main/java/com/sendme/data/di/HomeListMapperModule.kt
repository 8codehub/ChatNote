package com.sendme.data.di

import com.pingpad.coredomain.navigation.mapper.Mapper
import com.pingpad.coredomain.navigation.models.FolderBaseInfo
import com.sendme.data.mapping.FolderEntityToFolderBaseInfoMapper
import com.sendme.data.mapping.FolderEntityToFolderMapper
import com.sendme.data.mapping.FolderToFolderEntityMapper
import com.sendme.data.models.FolderEntity
import com.sendme.domain.model.Folder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeListMapperModule {
    // Provides the mapper to convert FolderEntity to Folder
    @Provides
    @Singleton
    fun provideFolderEntityToFolderMapper(): Mapper<FolderEntity, Folder> {
        return FolderEntityToFolderMapper()
    }

    // Provides the mapper to convert Folder to FolderEntity
    @Provides
    @Singleton
    fun provideFolderToFolderEntityMapper(): Mapper<Folder, FolderEntity> {
        return FolderToFolderEntityMapper()
    }

    // Provides the mapper to convert Folder to FolderBaseInfo
    @Provides
    @Singleton
    fun provideFolderToFolderBaseInfoMapper(): Mapper<FolderEntity, FolderBaseInfo> {
        return FolderEntityToFolderBaseInfoMapper()
    }
}