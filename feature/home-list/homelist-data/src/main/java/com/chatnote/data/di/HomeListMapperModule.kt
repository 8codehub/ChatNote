package com.chatnote.data.di

import com.chatnote.coredomain.mapper.Mapper
import com.chatnote.coredomain.models.FolderBaseInfo
import com.chatnote.data.mapper.FolderEntityToFolderBaseInfoMapper
import com.chatnote.data.mapper.FolderEntityToFolderMapper
import com.chatnote.data.mapper.FolderToFolderEntityMapper
import com.chatnote.data.models.FolderEntity
import com.chatnote.domain.model.Folder
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
