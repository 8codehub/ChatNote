package com.chatnote.coreui.di

import com.chatnote.coredomain.mapper.Mapper
import com.chatnote.coreui.mapper.ResultErrorToErrorMessageMapper

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
