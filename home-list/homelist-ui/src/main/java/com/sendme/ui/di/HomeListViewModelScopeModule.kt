package com.sendme.ui.di

import com.pingpad.coreui.arch.StatefulEventHandler
import com.sendme.ui.editor.FolderEditorContract
import com.sendme.ui.editor.FolderEditorContract.FolderEditorEvent
import com.sendme.ui.editor.FolderEditorContract.FolderEditorOneTimeEvent
import com.sendme.ui.editor.FolderEditorContract.FolderEditorState
import com.sendme.ui.editor.FolderEditorContract.MutableFolderEditorState
import com.sendme.ui.editor.FolderEditorStatefulEventHandler
import com.sendme.ui.folderlist.FolderListContract
import com.sendme.ui.folderlist.FolderListContract.FolderListEvent
import com.sendme.ui.folderlist.FolderListContract.FolderListOneTimeEvent
import com.sendme.ui.folderlist.FolderListContract.FolderListState
import com.sendme.ui.folderlist.FolderListContract.MutableFolderListState
import com.sendme.ui.folderlist.FolderListStatefulEventHandler
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class HomeListViewModelScopeModule {

    @Binds
    abstract fun bindFolderListStatefulEventHandler(
        implementation: FolderListStatefulEventHandler
    ): StatefulEventHandler<
            FolderListEvent,
            FolderListOneTimeEvent,
            FolderListState,
            MutableFolderListState
            >

    @Binds
    abstract fun bindFolderEditorStatefulEventHandler(
        implementation: FolderEditorStatefulEventHandler
    ): StatefulEventHandler<
            FolderEditorEvent,
            FolderEditorOneTimeEvent,
            FolderEditorState,
            MutableFolderEditorState
            >
}
