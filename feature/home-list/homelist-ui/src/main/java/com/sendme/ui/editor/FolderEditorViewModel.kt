package com.sendme.ui.editor

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.pingpad.coreui.arch.EventDrivenViewModel
import com.pingpad.coreui.arch.StatefulEventHandler
import com.sendme.common.di.IoDispatcher
import com.sendme.navigation.NavigationRoute
import com.sendme.ui.editor.FolderEditorContract.FolderEditorEvent
import com.sendme.ui.editor.FolderEditorContract.FolderEditorOneTimeEvent
import com.sendme.ui.editor.FolderEditorContract.FolderEditorState
import com.sendme.ui.editor.FolderEditorContract.MutableFolderEditorState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@HiltViewModel
class FolderEditorViewModel @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    savedStateHandle: SavedStateHandle,
    folderEditorStatefulEventHandler: StatefulEventHandler<
            FolderEditorEvent,
            FolderEditorOneTimeEvent,
            FolderEditorState,
            MutableFolderEditorState
            >
) : EventDrivenViewModel<
        FolderEditorState,
        MutableFolderEditorState,
        FolderEditorEvent,
        FolderEditorOneTimeEvent,
        StatefulEventHandler<
                FolderEditorEvent,
                FolderEditorOneTimeEvent,
                FolderEditorState,
                MutableFolderEditorState
                >
        >(statefulEventHandler = folderEditorStatefulEventHandler, ioDispatcher = dispatcher) {

    private var args = savedStateHandle.toRoute<NavigationRoute.FolderEditor>()

    fun done(name: String, selectedIconUri: String) {
        FolderEditorEvent.EditOrAddFolder(
            name = name,
            iconUri = selectedIconUri
        ).processWithLaunch()

    }

    suspend fun onTextChanged() {
        FolderEditorEvent.InputTextChanged.process()
    }

    override fun onStateReady() {
        FolderEditorEvent.LoadFolderInitialState(args.folderId != null).processWithLaunch()
        args.folderId?.let {
            FolderEditorEvent.LoadFolder(folderId = it).processWithLaunch()
        }
    }

    override fun onGeneralError(throwable: Throwable) {
        FolderEditorEvent.GeneralError(throwable = throwable).processWithLaunch()
    }

}
