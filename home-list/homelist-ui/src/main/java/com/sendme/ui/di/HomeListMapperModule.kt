package com.sendme.ui.di

import com.pingpad.coredomain.mapper.Mapper
import com.pingpad.coreui.util.DateFormatter
import com.sendme.domain.model.Folder
import com.sendme.ui.mapping.FolderDomainToUiFolderMapper
import com.sendme.ui.model.UiFolder
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
