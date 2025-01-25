package com.sendme.directnotesui

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.pingpad.coreui.arch.BaseViewModel
import com.pingpad.coreui.arch.StatefulEventHandler
import com.sendme.directnotesui.DirectNotesContract.DirectNotesEvent
import com.sendme.directnotesui.DirectNotesContract.DirectNotesOneTimeEvent
import com.sendme.directnotesui.DirectNotesContract.MutableDirectNotesState
import com.sendme.navigation.NavigationRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DirectNotesViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    statefulEventHandler: StatefulEventHandler<
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
        >(statefulEventHandler) {

    private val args = savedStateHandle.toRoute<NavigationRoute.DirectNotes>()

    private fun loadFolderData(folderId: Long) {
        DirectNotesEvent.LoadFolderBasicInfo(folderId).processWithLaunch()
        DirectNotesEvent.LoadAllNotes(folderId).processWithLaunch()
    }

    fun addNote(note: String) {
        DirectNotesEvent.AddNote(note).processWithLaunch()
    }

    override fun onStateReady() {
        loadFolderData(args.folderId)
    }
}
