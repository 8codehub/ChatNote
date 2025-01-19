package com.sendme.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sendme.domain.model.Folder
import com.sendme.domain.usecase.CreateFolderUseCase
import com.sendme.domain.usecase.GetFoldersUseCase
import com.sendme.ui.folderlist.FolderListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeListViewModel @Inject constructor(
    private val createFolderUseCase: CreateFolderUseCase,
    private val getFoldersUseCase: GetFoldersUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(FolderListState(folders = emptyList()))
    val state: StateFlow<FolderListState> = _state

    // Channel for navigation events
    private val _navigationEvent = Channel<Long>(Channel.BUFFERED)
    val navigationEvent = _navigationEvent.receiveAsFlow()

    init {
        fetchFolders()
    }

    private fun fetchFolders() {
        viewModelScope.launch {
            getFoldersUseCase().collect { folderList ->
                _state.update { currentState ->
                    currentState.copy(folders = folderList)
                }
            }
        }
    }

    fun createNewFolder(name: String) {
        viewModelScope.launch {
            val newFolderId = createFolderUseCase(name, "")
            _navigationEvent.send(newFolderId)
        }
    }
}
