package com.chatnote.directnotesui.di

import com.chatnote.directnotesui.processing.ExtraProcessor
import com.chatnote.directnotesui.processing.ExtraProcessorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class ProcessorModule {

    @Binds
    abstract fun bindEditNoteContentValidator(
        implementation: ExtraProcessorImpl
    ): ExtraProcessor
}
