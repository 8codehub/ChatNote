package com.chatnote.ui.folderlist

import android.content.Context
import com.chatnote.common.di.IoDispatcher
import com.chatnote.coreui.arch.EventDrivenViewModel
import com.chatnote.coreui.arch.StatefulEventHandler
import com.chatnote.ui.folderlist.FolderListContract.FolderListEvent
import com.chatnote.ui.folderlist.FolderListContract.FolderListOneTimeEvent
import com.chatnote.ui.folderlist.FolderListContract.FolderListState
import com.chatnote.ui.folderlist.FolderListContract.MutableFolderListState
import com.chatnote.ui.util.DefaultFoldersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject


@HiltViewModel
class FolderListViewModel @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    folderListStatefulEventHandler: StatefulEventHandler<
            FolderListEvent,
            FolderListOneTimeEvent,
            FolderListState,
            MutableFolderListState
            >
) : EventDrivenViewModel<FolderListState, MutableFolderListState, FolderListEvent, FolderListOneTimeEvent,
        StatefulEventHandler<
                FolderListEvent,
                FolderListOneTimeEvent,
                FolderListState,
                MutableFolderListState
                >
        >(statefulEventHandler = folderListStatefulEventHandler, ioDispatcher = dispatcher) {


    fun pinFolder(folderId: Long) {
        FolderListEvent.PinFolder(folderId = folderId).processWithLaunch()
    }

    fun deleteFolder(folderId: Long?) {
        folderId?.let {
            FolderListEvent.DeleteFolder(folderId = it).processWithLaunch()
        }
    }

    fun unPinFolder(folderId: Long) {
        FolderListEvent.UnpinFolder(folderId = folderId).processWithLaunch()
    }

    fun onAppFirstOpen(context: Context) {
        FolderListEvent.AddDefaultFolders(
            defaultFolders = DefaultFoldersProvider.loadFolders(
                context = context
            )
        ).processWithLaunch()

    }

    override fun onStateReady() {
        FolderListEvent.LoadFolders.processWithLaunch()
    }

    override fun onGeneralError(throwable: Throwable) {
    }
}