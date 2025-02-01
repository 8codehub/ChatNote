package com.chatnote.ui.di

import com.chatnote.coreui.arch.StatefulEventHandler
import com.chatnote.ui.editor.FolderEditorContract.FolderEditorEvent
import com.chatnote.ui.editor.FolderEditorContract.FolderEditorOneTimeEvent
import com.chatnote.ui.editor.FolderEditorContract.FolderEditorState
import com.chatnote.ui.editor.FolderEditorContract.MutableFolderEditorState
import com.chatnote.ui.editor.FolderEditorStatefulEventHandler
import com.chatnote.ui.folderlist.FolderListContract.FolderListEvent
import com.chatnote.ui.folderlist.FolderListContract.FolderListOneTimeEvent
import com.chatnote.ui.folderlist.FolderListContract.FolderListState
import com.chatnote.ui.folderlist.FolderListContract.MutableFolderListState
import com.chatnote.ui.folderlist.FolderListStatefulEventHandler
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
