package com.chatnote.coreui.di

import com.chatnote.coreui.systemactions.ClipboardActions
import com.chatnote.coreui.systemactions.ClipboardActionsImpl
import com.chatnote.coreui.systemactions.IntentActions
import com.chatnote.coreui.systemactions.IntentActionsImpl
import com.chatnote.coreui.systemactions.SystemActionHandlerImpl
import com.chatnote.coreui.systemactions.SystemActionTypeHandler
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class SystemActionsModule {

    @Binds
    @Singleton
    abstract fun bindSystemActionsHandler(
        impl: SystemActionHandlerImpl
    ): SystemActionTypeHandler

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