package com.sendme.ui.di

import com.pingpad.coreui.arch.StatefulEventHandler
import com.sendme.ui.folderlist.FolderListContract
import com.sendme.ui.folderlist.FolderListStatefulEventHandler
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class HomeListViewModelScopeModule {

    @Binds
    abstract fun bindGetFoldersUseCase(
        implementation: FolderListStatefulEventHandler
    ): StatefulEventHandler<FolderListContract.FolderListEvent, FolderListContract.FolderListState, FolderListContract.MutableFolderListState>

}