package com.sendme.domain.di

import com.sendme.domain.usecase.AddOrUpdateFolderUseCase
import com.sendme.domain.usecase.AddOrUpdateFolderUseCaseImpl
import com.sendme.domain.usecase.DeleteFolderUseCase
import com.sendme.domain.usecase.DeleteFolderUseCaseImpl
import com.sendme.domain.usecase.GetFolderByIdUseCase
import com.sendme.domain.usecase.GetFolderByIdUseCaseImpl
import com.sendme.domain.usecase.GetFoldersUseCase
import com.sendme.domain.usecase.GetFoldersUseCaseImpl
import com.sendme.domain.usecase.PinFolderUseCase
import com.sendme.domain.usecase.PinFolderUseCaseImpl
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
    abstract fun bindPinFolderUseCase(
        implementation: PinFolderUseCaseImpl
    ): PinFolderUseCase

    @Binds
    abstract fun bindUnpinFolderUseCase(
        implementation: UnpinFolderUseCaseImpl
    ): UnpinFolderUseCase

    @Binds
    abstract fun bindDeleteFolderUseCase(
        implementation: DeleteFolderUseCaseImpl
    ): DeleteFolderUseCase

    @Binds
    abstract fun bindAddOrUpdateFolderUseCase(
        implementation: AddOrUpdateFolderUseCaseImpl
    ): AddOrUpdateFolderUseCase

}
