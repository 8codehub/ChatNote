package com.sendme.ui.folderlist

import com.pingpad.coreui.arch.BaseViewModel
import com.pingpad.coreui.arch.StatefulEventHandler
import com.sendme.ui.folderlist.FolderListContract.FolderListEvent
import com.sendme.ui.folderlist.FolderListContract.FolderListOneTimeEvent
import com.sendme.ui.folderlist.FolderListContract.FolderListState
import com.sendme.ui.folderlist.FolderListContract.MutableFolderListState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class FolderListViewModel @Inject constructor(
    folderListStatefulEventHandler: StatefulEventHandler<
            FolderListEvent,
            FolderListOneTimeEvent,
            FolderListState,
            MutableFolderListState
            >
) : BaseViewModel<FolderListState, MutableFolderListState, FolderListEvent, FolderListOneTimeEvent,
        StatefulEventHandler<
                FolderListEvent,
                FolderListOneTimeEvent,
                FolderListState,
                MutableFolderListState
                >
        >(folderListStatefulEventHandler) {

    init {
        FolderListEvent.LoadFolders.processWithLaunch()
    }

    fun pinFolder(folderId: Long) {
        FolderListEvent.PinFolder(folderId = folderId).processWithLaunch()
    }

    fun unPinFolder(folderId: Long) {
        FolderListEvent.UnpinFolder(folderId = folderId).processWithLaunch()
    }
}