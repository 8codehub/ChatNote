package com.sendme.data.di

import com.pingpad.coredomain.mapper.Mapper
import com.pingpad.coredomain.models.FolderBaseInfo
import com.sendme.data.mapper.FolderEntityToFolderBaseInfoMapper
import com.sendme.data.mapper.FolderEntityToFolderMapper
import com.sendme.data.mapper.FolderToFolderEntityMapper
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

    @Provides
    @Singleton
    fun provideFolderEntityToFolderMapper(): Mapper<FolderEntity, Folder> =
        FolderEntityToFolderMapper()

    @Provides
    @Singleton
    fun provideFolderToFolderEntityMapper(): Mapper<Folder, FolderEntity> =
        FolderToFolderEntityMapper()

    @Provides
    @Singleton
    fun provideFolderToFolderBaseInfoMapper(): Mapper<FolderEntity, FolderBaseInfo> =
        FolderEntityToFolderBaseInfoMapper()
}
