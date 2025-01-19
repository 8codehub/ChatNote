package com.sendme.domain.di

import com.sendme.domain.validator.NewFolderNameValidator
import com.sendme.domain.validator.NewFolderNameValidatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class ValidatorModule {

    @Binds
    abstract fun bindNewFolderNameValidator(
        implementation: NewFolderNameValidatorImpl
    ): NewFolderNameValidator

}