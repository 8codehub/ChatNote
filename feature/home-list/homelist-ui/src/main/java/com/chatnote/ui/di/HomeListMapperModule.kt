package com.chatnote.ui.di

import com.chatnote.coredomain.mapper.Mapper
import com.chatnote.coreui.util.DateFormatter
import com.chatnote.domain.model.Folder
import com.chatnote.ui.mapping.FolderDomainToUiFolderMapper
import com.chatnote.ui.model.UiFolder
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
    fun provideResultErrorToErrorMessageIdMapper(dateFormatter: DateFormatter): Mapper<Folder, UiFolder> {
        return FolderDomainToUiFolderMapper(dateFormatter = dateFormatter)
    }
}
