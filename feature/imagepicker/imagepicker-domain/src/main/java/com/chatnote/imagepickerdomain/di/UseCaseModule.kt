package com.chatnote.imagepickerdomain.di

import com.chatnote.imagepickerdomain.usecase.CopyImageToAppStorage
import com.chatnote.imagepickerdomain.usecase.CopyImageToAppStorageImpl
import com.chatnote.imagepickerdomain.usecase.GenerateCameraUriUseCase
import com.chatnote.imagepickerdomain.usecase.GenerateCameraUriUseCaseImpl
import com.chatnote.imagepickerdomain.usecase.GetRecentImagesUseCase
import com.chatnote.imagepickerdomain.usecase.GetRecentImagesUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class UseCaseModule {

    @Binds
    abstract fun bindGGenerateCameraUriUseCase(
        implementation: GenerateCameraUriUseCaseImpl
    ): GenerateCameraUriUseCase

    @Binds
    abstract fun bindGetRecentImagesUseCase(
        implementation: GetRecentImagesUseCaseImpl
    ): GetRecentImagesUseCase

    @Binds
    abstract fun bindCopyImageToAppStorage(
        implementation: CopyImageToAppStorageImpl
    ): CopyImageToAppStorage

}