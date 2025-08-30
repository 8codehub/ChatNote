package com.chatnote.directnotesdomain.di

import com.chatnote.directnotesdomain.validator.EditNoteContentValidator
import com.chatnote.directnotesdomain.validator.EditNoteContentValidatorImpl
import com.chatnote.directnotesdomain.validator.NewNoteValidator
import com.chatnote.directnotesdomain.validator.NewNoteValidatorImpl
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


    @Binds
    abstract fun bindNewNoteValidator(
        implementation: NewNoteValidatorImpl
    ): NewNoteValidator
}
