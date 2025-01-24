package com.sendme.ui.editor.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.pingpad.coreui.arch.BaseViewModel
import com.pingpad.coreui.arch.StatefulEventHandler
import com.sendme.navigation.NavigationRoute
import com.sendme.ui.editor.FolderEditorContract.FolderEditorEvent
import com.sendme.ui.editor.FolderEditorContract.FolderEditorOneTimeEvent
import com.sendme.ui.editor.FolderEditorContract.FolderEditorState
import com.sendme.ui.editor.FolderEditorContract.MutableFolderEditorState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FolderEditorViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    folderEditorStatefulEventHandler: StatefulEventHandler<
            FolderEditorEvent,
            FolderEditorOneTimeEvent,
            FolderEditorState,
            MutableFolderEditorState
            >
) : BaseViewModel<
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
        >(folderEditorStatefulEventHandler) {

    private var args = savedStateHandle.toRoute<NavigationRoute.FolderEditor>()

    init {
        FolderEditorEvent.LoadFolderInitialState(args.folderId != null).processWithLaunch()
        args.folderId?.let {
            FolderEditorEvent.LoadFolder(folderId = it).processWithLaunch()
        }
    }

    fun done(name: String, selectedIconUri: String) {
        FolderEditorEvent.EditOrAddFolder(
            name = name,
            iconUri = selectedIconUri
        ).processWithLaunch()

    }

}
