package com.sendme.directnotsdomain.di

import com.sendme.directnotsdomain.usecase.AddNoteUseCase
import com.sendme.directnotsdomain.usecase.AddNoteUseCaseImpl
import com.sendme.directnotsdomain.usecase.GetNotesUseCase
import com.sendme.directnotsdomain.usecase.GetNotesUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class UseCaseModule {

    @Binds
    abstract fun bindGetFoldersUseCase(
        implementation: GetNotesUseCaseImpl
    ): GetNotesUseCase

    @Binds
    abstract fun bindCreateFolderUseCase(
        implementation: AddNoteUseCaseImpl
    ): AddNoteUseCase
}