package com.chatnote.coredata.di.di

import com.chatnote.coredata.di.util.AppPreferencesImpl
import com.chatnote.coredata.di.util.SharedPreferencesHelper
import com.chatnote.coredata.di.util.SharedPreferencesHelperImpl
import com.chatnote.coredomain.utils.AppPreferences
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class CoreDataModule {

    @Binds
    abstract fun bindSharedPreferencesHelper(
        impl: SharedPreferencesHelperImpl
    ): SharedPreferencesHelper

    @Binds
    abstract fun bindAppPreferences(
        impl: AppPreferencesImpl
    ): AppPreferences
}