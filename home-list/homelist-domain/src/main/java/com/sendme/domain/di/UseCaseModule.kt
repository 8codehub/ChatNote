package com.sendme.domain.di

import com.sendme.domain.usecase.AddOrUpdateFolderUseCase
import com.sendme.domain.usecase.AddOrUpdateFolderUseCaseImpl
import com.sendme.domain.usecase.GetFolderByIdUseCase
import com.sendme.domain.usecase.GetFolderByIdUseCaseImpl
import com.sendme.domain.usecase.GetFoldersUseCase
import com.sendme.domain.usecase.GetFoldersUseCaseImpl
import com.sendme.domain.usecase.GetFolderIconsUseCase
import com.sendme.domain.usecase.GetFolderIconsUseCaseImpl
import com.sendme.domain.usecase.PinFolderUseCaseImpl
import com.sendme.domain.usecase.PinFolderUseCase
import com.sendme.domain.usecase.UnpinFolderUseCase
import com.sendme.domain.usecase.UnpinFolderUseCaseImpl
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
    abstract fun bindGetFolderByIdUseCase(
        implementation: GetFolderByIdUseCaseImpl
    ): GetFolderByIdUseCase

    @Binds
    abstract fun bindUnpinFolderUseCase(
        implementation: PinFolderUseCaseImpl
    ): PinFolderUseCase

    @Binds
    abstract fun bindPinFolderUseCase(
        implementation: UnpinFolderUseCaseImpl
    ): UnpinFolderUseCase

    @Binds
    abstract fun bindCreateFolderUseCase(
        implementation: AddOrUpdateFolderUseCaseImpl
    ): AddOrUpdateFolderUseCase

    @Binds
    abstract fun bindGetIconUrisUseCase(
        implementation: GetFolderIconsUseCaseImpl
    ): GetFolderIconsUseCase
}