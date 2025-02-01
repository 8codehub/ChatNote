package com.sendme.directnotesui.di

import com.pingpad.coredomain.mapper.Mapper
import com.pingpad.coreui.util.DateFormatter
import com.sendme.directnotesui.mapper.NotesToUiNotesMapper
import com.sendme.directnotesui.model.UiNote
import com.sendme.directnotsdomain.model.Note
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
