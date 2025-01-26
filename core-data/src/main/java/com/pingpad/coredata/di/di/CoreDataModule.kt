package com.pingpad.coredata.di.di

import com.pingpad.coredata.di.util.AppPreferencesImpl
import com.pingpad.coredata.di.util.SharedPreferencesHelper
import com.pingpad.coredata.di.util.SharedPreferencesHelperImpl
import com.pingpad.coredomain.utils.AppPreferences
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CoreDataModule {

    @Binds
    abstract fun bindSharedPreferencesHelper(
        impl: SharedPreferencesHelperImpl
    ): SharedPreferencesHelper

    @Binds
    abstract fun bindAppPreferences(
        impl: AppPreferencesImpl
    ): AppPreferences

}