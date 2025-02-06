package com.chatnote.coreui.di

import com.chatnote.coreui.systemactions.ClipboardActions
import com.chatnote.coreui.systemactions.ClipboardActionsImpl
import com.chatnote.coreui.systemactions.IntentActions
import com.chatnote.coreui.systemactions.IntentActionsImpl
import com.chatnote.coreui.systemactions.SystemActionsHandler
import com.chatnote.coreui.systemactions.SystemActionsHandlerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SystemActionsModule {

    @Binds
    @Singleton
    abstract fun bindSystemActionsHandler(
        impl: SystemActionsHandlerImpl
    ): SystemActionsHandler

    @Binds
    @Singleton
    abstract fun bindClipboardActions(
        impl: ClipboardActionsImpl
    ): ClipboardActions

    @Binds
    @Singleton
    abstract fun bindIntentActions(
        impl: IntentActionsImpl
    ): IntentActions
}