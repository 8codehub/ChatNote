package com.chatnote.directnotesdomain.di

import com.chatnote.directnotesdomain.usecase.AddNoteUseCase
import com.chatnote.directnotesdomain.usecase.AddNoteUseCaseImpl
import com.chatnote.directnotesdomain.usecase.ExtractActionableContentUseCase
import com.chatnote.directnotesdomain.usecase.ExtractActionableContentUseCaseImpl
import com.chatnote.directnotesdomain.usecase.GetNotesUseCase
import com.chatnote.directnotesdomain.usecase.GetNotesUseCaseImpl
import com.chatnote.directnotesdomain.usecase.ObserveFolderUseCase
import com.chatnote.directnotesdomain.usecase.ObserveFolderUseCaseImpl
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

    @Binds
    abstract fun bindObserveFolderUseCase(
        implementation: ObserveFolderUseCaseImpl
    ): ObserveFolderUseCase

    @Binds
    abstract fun bindExtractActionableContentUseCase(
        implementation: ExtractActionableContentUseCaseImpl
    ): ExtractActionableContentUseCase
}