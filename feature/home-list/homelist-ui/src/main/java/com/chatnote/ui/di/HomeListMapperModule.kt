package com.chatnote.ui.di

import com.chatnote.coredomain.mapper.Mapper
import com.chatnote.coredomain.models.NoteExtra
import com.chatnote.coreui.util.DateFormatter
import com.chatnote.domain.model.Folder
import com.chatnote.ui.mapping.FolderDomainToUiFolderMapper
import com.chatnote.ui.mapping.NoteExtraToUiNoteExtraMapper
import com.chatnote.ui.model.UiFolder
import com.chatnote.ui.model.UiNoteExtra
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeListMapperModule {

    @Provides
    @Singleton
    fun provideResultErrorToErrorMessageIdMapper(
        dateFormatter: DateFormatter,
        extraMapper: Mapper<NoteExtra, UiNoteExtra>
    ): Mapper<Folder, UiFolder> {
        return FolderDomainToUiFolderMapper(
            dateFormatter = dateFormatter,
            extraMapper = extraMapper
        )
    }


    @Provides
    @Singleton
    fun provideNoteExtraToUiNoteExtraMapper(): Mapper<NoteExtra, UiNoteExtra> {
        return NoteExtraToUiNoteExtraMapper()
    }
}
