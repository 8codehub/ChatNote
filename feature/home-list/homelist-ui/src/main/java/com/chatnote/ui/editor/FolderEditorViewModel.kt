package com.chatnote.ui.editor

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.chatnote.common.di.IoDispatcher
import com.chatnote.coreui.arch.EventDrivenViewModel
import com.chatnote.coreui.arch.StatefulEventHandler
import com.chatnote.navigation.NavigationRoute
import com.chatnote.ui.editor.FolderEditorContract.FolderEditorEvent
import com.chatnote.ui.editor.FolderEditorContract.FolderEditorOneTimeEvent
import com.chatnote.ui.editor.FolderEditorContract.FolderEditorState
import com.chatnote.ui.editor.FolderEditorContract.MutableFolderEditorState
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

    private var folderEditorRoute = savedStateHandle.toRoute<NavigationRoute.FolderEditor>()

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
        FolderEditorEvent.LoadFolderInitialState(folderEditorRoute.folderId != null)
            .processWithLaunch()
        folderEditorRoute.folderId?.let { route ->
            FolderEditorEvent.LoadFolder(folderId = route).processWithLaunch()
        }
    }

    override fun onGeneralError(throwable: Throwable) {
        FolderEditorEvent.GeneralError(throwable = throwable).processWithLaunch()
    }

}
