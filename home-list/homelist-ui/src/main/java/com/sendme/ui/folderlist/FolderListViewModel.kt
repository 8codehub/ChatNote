package com.sendme.ui.folderlist

import com.pingpad.coreui.arch.BaseViewModel
import com.pingpad.coreui.arch.StatefulEventHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class FolderListViewModel @Inject constructor(
    folderListStatefulEventHandler: StatefulEventHandler<
            FolderListContract.FolderListEvent,
            FolderListContract.FolderListState,
            FolderListContract.MutableFolderListState
            >
) : BaseViewModel<
        FolderListContract.FolderListState,
        FolderListContract.MutableFolderListState,
        FolderListContract.FolderListEvent,
        FolderListContract.FolderListOneTimeEvent,
        StatefulEventHandler<
                FolderListContract.FolderListEvent,
                FolderListContract.FolderListState,
                FolderListContract.MutableFolderListState
                >
        >(folderListStatefulEventHandler) {

    init {
        FolderListContract.FolderListEvent.LoadFolders.processWithLaunch()
    }
}