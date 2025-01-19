package com.sendme.ui.newfolder.viewmodel

import androidx.annotation.StringRes
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.sendme.coredomain.navigation.validator.ErrorKey
import com.sendme.coredomain.navigation.validator.ValidationResult
import com.sendme.domain.usecase.CreateFolderUseCase
import com.sendme.domain.usecase.GetFolderIconsUseCase
import com.sendme.domain.validator.NewFolderNameValidator
import com.sendme.navigation.NavigationRoute
import com.sendme.navigation.UiEvent
import com.sendme.ui.newfolder.state.CreateNewFolderState
import com.sendme.ui.provider.ImageUrlProvider
import com.sendme.ui.validation.toErrorStringId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FolderEditorViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getFolderIconsUseCase: GetFolderIconsUseCase,
    private val createFolderUseCase: CreateFolderUseCase,
    private val imageUrlProvider: ImageUrlProvider,
    private val newFolderNameValidator: NewFolderNameValidator
) : ViewModel() {

    private val _state = MutableStateFlow(CreateNewFolderState())
    val state: StateFlow<CreateNewFolderState> = _state

    // Channel for navigation events
    private val _navigationEvent = Channel<UiEvent.Navigate>(Channel.BUFFERED)
    val navigationEvent = _navigationEvent.receiveAsFlow()

    init {
        var args = savedStateHandle.toRoute<NavigationRoute.FolderEditor>()
        fetchIcons()
    }


    private fun fetchIcons() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            _state.update {
                it.copy(icons = imageUrlProvider.getAllIcons()
                    .map { "file:///android_asset/icons/$it" })
            }

        }
    }

    fun createNewFolder(name: String, iconUri: String) {
        viewModelScope.launch {
            updateUiState(isLoading = true, inputError = null)
            when (val result = newFolderNameValidator(folderName = name)) {
                ValidationResult.Success -> {
                    setupNewFolder(name = name, iconUri = iconUri)
                }

                is ValidationResult.Error -> {
                    handleInvalidResult(errorKey = result.errorKey)
                }
            }
        }
    }

    private fun handleInvalidResult(errorKey: ErrorKey) {
        updateUiState(inputError = errorKey.toErrorStringId(), isLoading = false)
    }

    private suspend fun setupNewFolder(name: String, iconUri: String) {
        val newFolderId = createFolderUseCase(folderName = name, iconUri = iconUri)
        val uiEvent =
            UiEvent.Navigate(
                NavigationRoute.DirectNotes(
                    folderId = newFolderId,
                    folderName = name,
                    folderIconUri = iconUri
                )
            )
        updateUiState(isLoading = false)
        _navigationEvent.send(uiEvent)
    }

    private fun updateUiState(
        folderName: String = _state.value.folderName,
        icons: List<String> = _state.value.icons,
        isLoading: Boolean = _state.value.isLoading,
        @StringRes inputError: Int? = _state.value.inputError
    ) {
        _state.update {
            it.copy(
                folderName = folderName,
                icons = icons,
                isLoading = isLoading,
                inputError = inputError
            )
        }
    }

    fun onFolderNameChanged(folderName: String) {
        updateUiState(folderName = folderName, inputError = null)
    }
}
