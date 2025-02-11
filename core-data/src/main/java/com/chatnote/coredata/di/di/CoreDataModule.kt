package com.chatnote.coredata.di.di

import com.chatnote.coredata.di.util.AppPreferencesSyncImpl
import com.chatnote.coredata.di.util.SharedPreferencesHelper
import com.chatnote.coredata.di.util.SharedPreferencesHelperImpl
import com.chatnote.coredomain.utils.AppPreferencesSync
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
    abstract fun bindAppPreferencesAsync(
        impl: AppPreferencesSyncImpl
    ): AppPreferencesSync

}