package com.chatnote.directnotesui.di

import com.chatnote.coredomain.mapper.Mapper
import com.chatnote.coreui.util.DateFormatter
import com.chatnote.directnotesdomain.model.Note
import com.chatnote.directnotesui.mapper.NotesToUiNotesMapper
import com.chatnote.directnotesui.model.UiNote
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DirectNotesMapperModule {

    @Provides
    @Singleton
    fun provideFolderEntityToFolderMapper(dateFormatter: DateFormatter): Mapper<Note, UiNote> =
        NotesToUiNotesMapper(dateFormatter = dateFormatter)

}
