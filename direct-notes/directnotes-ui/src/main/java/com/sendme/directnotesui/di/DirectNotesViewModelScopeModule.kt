package com.sendme.directnotesui.di

import com.pingpad.coreui.arch.StatefulEventHandler
import com.sendme.directnotesui.DirectNotesContract
import com.sendme.directnotesui.DirectNotesContract.DirectNotesEvent
import com.sendme.directnotesui.DirectNotesContract.DirectNotesOneTimeEvent
import com.sendme.directnotesui.DirectNotesContract.DirectNotesState
import com.sendme.directnotesui.DirectNotesContract.MutableDirectNotesState
import com.sendme.directnotesui.DirectNotesStatefulEventHandler
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class DirectNotesViewModelScopeModule {

    @Binds
    abstract fun bindFolderListStatefulEventHandler(
        implementation: DirectNotesStatefulEventHandler
    ): StatefulEventHandler<DirectNotesEvent, DirectNotesOneTimeEvent, DirectNotesState, MutableDirectNotesState>

}