package com.chatnote.directnotesdomain.di

import com.chatnote.directnotesdomain.validator.EditNoteContentValidator
import com.chatnote.directnotesdomain.validator.EditNoteContentValidatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class ValidatorModule {

    @Binds
    abstract fun bindEditNoteContentValidator(
        implementation: EditNoteContentValidatorImpl
    ): EditNoteContentValidator
}
