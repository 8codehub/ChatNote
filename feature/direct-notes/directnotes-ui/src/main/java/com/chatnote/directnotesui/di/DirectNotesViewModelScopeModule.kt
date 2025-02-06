package com.chatnote.directnotesui.di

import com.chatnote.coreui.arch.StatefulEventHandler
import com.chatnote.directnotesui.DirectNotesStatefulEventHandler
import com.chatnote.directnotesui.directnoteslist.DirectNotesContract.DirectNotesEvent
import com.chatnote.directnotesui.directnoteslist.DirectNotesContract.DirectNotesOneTimeEvent
import com.chatnote.directnotesui.directnoteslist.DirectNotesContract.DirectNotesState
import com.chatnote.directnotesui.directnoteslist.DirectNotesContract.MutableDirectNotesState
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class DirectNotesViewModelScopeModule {

    @Binds
    abstract fun bindDirectNotesStatefulEventHandler(
        implementation: DirectNotesStatefulEventHandler
    ): StatefulEventHandler<
            DirectNotesEvent,
            DirectNotesOneTimeEvent,
            DirectNotesState,
            MutableDirectNotesState
            >
}
