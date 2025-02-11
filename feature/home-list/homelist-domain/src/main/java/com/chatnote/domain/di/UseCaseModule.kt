package com.chatnote.domain.di

import com.chatnote.domain.usecase.AddOrUpdateFolderUseCase
import com.chatnote.domain.usecase.AddOrUpdateFolderUseCaseImpl
import com.chatnote.domain.usecase.DeleteFolderUseCase
import com.chatnote.domain.usecase.DeleteFolderUseCaseImpl
import com.chatnote.domain.usecase.GetFolderByIdUseCase
import com.chatnote.domain.usecase.GetFolderByIdUseCaseImpl
import com.chatnote.domain.usecase.GetFoldersUseCase
import com.chatnote.domain.usecase.GetFoldersUseCaseImpl
import com.chatnote.domain.usecase.GetOnboardingStatusUseCase
import com.chatnote.domain.usecase.GetOnboardingStatusUseCaseImpl
import com.chatnote.domain.usecase.InitializeDefaultFoldersUseCase
import com.chatnote.domain.usecase.InitializeDefaultFoldersUseCaseImpl
import com.chatnote.domain.usecase.PinFolderUseCase
import com.chatnote.domain.usecase.PinFolderUseCaseImpl
import com.chatnote.domain.usecase.SetOnboardingStatusUseCase
import com.chatnote.domain.usecase.SetOnboardingStatusUseCaseImpl
import com.chatnote.domain.usecase.UnpinFolderUseCase
import com.chatnote.domain.usecase.UnpinFolderUseCaseImpl
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

    @Binds
    abstract fun bindInitializeDefaultFoldersUseCase(
        implementation: InitializeDefaultFoldersUseCaseImpl
    ): InitializeDefaultFoldersUseCase

    @Binds
    abstract fun bindGetOnboardingStatusUseCase(
        implementation: GetOnboardingStatusUseCaseImpl
    ): GetOnboardingStatusUseCase

    @Binds
    abstract fun bindGSetOnboardingStatusUseCase(
        implementation: SetOnboardingStatusUseCaseImpl
    ): SetOnboardingStatusUseCase

}
