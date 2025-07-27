package com.chatnote.coredomain.di

import com.chatnote.coredomain.manager.FileManager
import com.chatnote.coredomain.manager.FileManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class CoreUseCaseModule {

    @Provides
    @Singleton
    fun provideDeleteFileIfExistsUseCase(): FileManager {
        return FileManagerImpl()
    }
}
