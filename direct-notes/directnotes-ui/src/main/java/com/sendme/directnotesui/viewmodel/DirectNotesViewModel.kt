package com.sendme.directnotesui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.pingpad.coreui.arch.BaseViewModel
import com.pingpad.coreui.arch.StatefulEventHandler
import com.sendme.directnotesui.DirectNotesContract
import com.sendme.directnotesui.DirectNotesContract.DirectNotesEvent
import com.sendme.directnotesui.DirectNotesContract.DirectNotesOneTimeEvent
import com.sendme.directnotesui.DirectNotesContract.MutableDirectNotesState
import com.sendme.navigation.NavigationRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DirectNotesViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    folderEditorStatefulEventHandler: StatefulEventHandler<
            DirectNotesEvent,
            DirectNotesOneTimeEvent,
            DirectNotesContract.DirectNotesState,
            MutableDirectNotesState
            >
) : BaseViewModel<
        DirectNotesContract.DirectNotesState,
        MutableDirectNotesState,
        DirectNotesEvent,
        DirectNotesOneTimeEvent,
        StatefulEventHandler<
                DirectNotesEvent,
                DirectNotesOneTimeEvent,
                DirectNotesContract.DirectNotesState,
                MutableDirectNotesState
                >
        >(folderEditorStatefulEventHandler) {

    private var args = savedStateHandle.toRoute<NavigationRoute.DirectNotes>()

    init {
        DirectNotesEvent.InitData(
            folderId = args.folderId,
            folderName = args.folderName,
            folderIconUri = args.folderIconUri
        ).processWithLaunch()
    }

    fun addNote(note: String) {
        DirectNotesEvent.AddNote( note = note).processWithLaunch()
    }

}
