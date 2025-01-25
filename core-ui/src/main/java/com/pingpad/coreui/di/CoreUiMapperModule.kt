package com.pingpad.coreui.di

import com.pingpad.coredomain.mapper.Mapper
import com.pingpad.coreui.mapping.ResultErrorToErrorMessageMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreUiMapperModule {

    @Provides
    @Singleton
    fun provideResultErrorToErrorMessageIdMapper(): Mapper<Throwable?, Int> {
        return ResultErrorToErrorMessageMapper()
    }
}
