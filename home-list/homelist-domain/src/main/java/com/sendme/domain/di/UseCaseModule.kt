package com.sendme.domain.di

import com.sendme.domain.usecase.CreateFolderUseCase
import com.sendme.domain.usecase.CreateFolderUseCaseImpl
import com.sendme.domain.usecase.GetFoldersUseCase
import com.sendme.domain.usecase.GetFoldersUseCaseImpl
import com.sendme.domain.usecase.GetFolderIconsUseCase
import com.sendme.domain.usecase.GetFolderIconsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class UseCaseModule {

    @Binds
    abstract fun bindGetFoldersUseCase(
        implementation: GetFoldersUseCaseImpl
    ): GetFoldersUseCase

    @Binds
    abstract fun bindCreateFolderUseCase(
        implementation: CreateFolderUseCaseImpl
    ): CreateFolderUseCase

    @Binds
    abstract fun bindGetIconUrisUseCase(
        implementation: GetFolderIconsUseCaseImpl
    ): GetFolderIconsUseCase
}