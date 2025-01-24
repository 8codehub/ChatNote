package com.sendme.ui.editor

import com.pingpad.coreui.arch.StatefulEventHandler
import com.sendme.coredomain.navigation.validator.ErrorKey
import com.sendme.coredomain.navigation.validator.ValidationResult
import com.sendme.domain.usecase.AddOrUpdateFolderUseCase
import com.sendme.domain.usecase.GetFolderByIdUseCase
import com.sendme.domain.usecase.GetFolderIconsUseCase
import com.sendme.domain.validator.NewFolderNameValidator
import com.sendme.homelistui.R
import com.sendme.navigation.NavigationRoute
import com.sendme.ui.editor.FolderEditorContract.FolderEditorEvent
import com.sendme.ui.editor.FolderEditorContract.FolderEditorOneTimeEvent
import com.sendme.ui.editor.FolderEditorContract.FolderEditorState
import com.sendme.ui.editor.FolderEditorContract.MutableFolderEditorState
import com.sendme.ui.provider.ImageUrlProvider
import com.sendme.ui.validation.toErrorStringId
import javax.inject.Inject


class FolderEditorStatefulEventHandler @Inject constructor(
    private val getFolderIconsUseCase: GetFolderIconsUseCase,
    private val addOrUpdateFolder: AddOrUpdateFolderUseCase,
    private val getFolderById: GetFolderByIdUseCase,
    private val imageUrlProvider: ImageUrlProvider,
    private val newFolderNameValidator: NewFolderNameValidator
) : StatefulEventHandler<FolderEditorEvent, FolderEditorOneTimeEvent, FolderEditorState, MutableFolderEditorState>(
    FolderEditorState()
) {

    override suspend fun process(event: FolderEditorEvent, args: Any?) {
        when (event) {
            is FolderEditorEvent.EditOrAddFolder -> onEditOrAddFolderEvent(
                name = event.name,
                iconUri = event.iconUri
            )

            is FolderEditorEvent.LoadFolderInitialState -> onLoadFolderInitialStateEvent(isEditMode = event.isEditMode)
            is FolderEditorEvent.LoadFolder -> onLoadFolderEvent(folderId = event.folderId)
        }

    }

    private suspend fun onLoadFolderEvent(folderId: Long) {
        updateUiState { isLoading = true }
        val result = getFolderById(folderId = folderId)
        val folder = result.getOrNull()
        if (result.isSuccess && folder != null) {
            updateUiState {
                isLoading = false
                this.folderId = folder.id
                folderName = folder.name
                folderIconUri = folder.iconUri
            }
        } else {
            updateUiState {
                isLoading = false
                inputError = R.string.general_error
            }
        }
    }

    private suspend fun onEditOrAddFolderEvent(
        name: String,
        iconUri: String
    ) {

        updateUiState {
            inputError = null
        }

        when (val result = newFolderNameValidator(folderName = name)) {
            ValidationResult.Success -> {
                handleValidFolderData(name = name, iconUri = iconUri)
            }

            is ValidationResult.Error -> {
                handleInvalidResult(errorKey = result.errorKey)
            }
        }
    }

    private fun onLoadFolderInitialStateEvent(isEditMode: Boolean) {
        updateUiState {
            title = if (isEditMode) (R.string.edit_details) else {
                R.string.new_folder
            }
            isLoading = true
        }
        val icons = imageUrlProvider.getAllIcons().map { "file:///android_asset/icons/$it" }
        updateUiState {
            isLoading = false
            this.icons = icons
        }
    }


    private fun handleInvalidResult(errorKey: ErrorKey) {
        updateUiState {
            inputError = errorKey.toErrorStringId()
            isLoading = false
        }
    }

    private suspend fun handleValidFolderData(name: String, iconUri: String) {

        val folderId =
            addOrUpdateFolder(folderId = stateValue.folderId, name = name, iconUri = iconUri)

        updateUiState {
            isLoading = false
        }

        stateValue.folderId?.let {
            _uiEvent.send(FolderEditorOneTimeEvent.NavigateBack)

        } ?: _uiEvent.send(
            FolderEditorOneTimeEvent.NavigateTo(
                NavigationRoute.DirectNotes(
                    folderId = folderId,
                    folderName = name,
                    folderIconUri = iconUri
                )
            )
        )

    }

    private fun onFolderNameInputChangedEvent(folderName: String) {
        updateUiState {
            this.folderName = folderName
            inputError = null
        }
    }


}