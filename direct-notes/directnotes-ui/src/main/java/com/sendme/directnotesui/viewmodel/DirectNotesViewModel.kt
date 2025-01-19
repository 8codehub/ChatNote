package com.sendme.directnotesui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.sendme.directnotesui.DirectNotesState
import com.sendme.directnotsdomain.SendMeNote
import com.sendme.directnotsdomain.usecase.AddNoteUseCase
import com.sendme.directnotsdomain.usecase.GetNotesUseCase
import com.sendme.navigation.NavigationRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DirectNotesViewModel @Inject constructor(
    private val stateHandle: SavedStateHandle,
    private val getNotesUseCase: GetNotesUseCase,
    private val addNoteUseCase: AddNoteUseCase
) : ViewModel() {


    private val _state = MutableStateFlow<DirectNotesState>(DirectNotesState())
    val state: StateFlow<DirectNotesState> = _state

    init {
        val args: NavigationRoute.DirectNotes = stateHandle.toRoute<NavigationRoute.DirectNotes>()
        loadNotes(folderName = args.folderName, folderId = args.folderId)
    }

    private fun loadNotes(folderName: String, folderId: Long) {
        viewModelScope.launch {
            _state.update { it.copy(folderId = folderId, folderName = folderName) }
            getNotesUseCase(folderId).collect { notesList ->
                _state.update { it.copy(notes = notesList) }
            }
        }
    }

    fun addNote(folderId: Long, content: String) {
        viewModelScope.launch {
            val newNote = SendMeNote(
                id = System.currentTimeMillis(),
                content = content
            )
            addNoteUseCase(folderId, newNote)
        }
    }
}
