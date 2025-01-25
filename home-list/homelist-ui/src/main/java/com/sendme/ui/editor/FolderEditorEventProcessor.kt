package com.sendme.ui.editor

import androidx.annotation.StringRes
import com.pingpad.coredomain.mapper.Mapper
import com.pingpad.coreui.arch.StatefulEventHandler
import com.sendme.domain.usecase.AddOrUpdateFolderUseCase
import com.sendme.domain.usecase.GetFolderByIdUseCase
import com.sendme.domain.validator.NewFolderNameValidator
import com.sendme.homelistui.R
import com.sendme.navigation.NavigationRoute
import com.sendme.ui.editor.FolderEditorContract.FolderEditorEvent
import com.sendme.ui.editor.FolderEditorContract.FolderEditorOneTimeEvent
import com.sendme.ui.editor.FolderEditorContract.FolderEditorState
import com.sendme.ui.editor.FolderEditorContract.MutableFolderEditorState
import com.sendme.ui.provider.ImageUrlProvider
import javax.inject.Inject


class FolderEditorStatefulEventHandler @Inject constructor(
    private val addOrUpdateFolder: AddOrUpdateFolderUseCase,
    private val getFolderById: GetFolderByIdUseCase,
    private val imageUrlProvider: ImageUrlProvider,
    private val newFolderNameValidator: NewFolderNameValidator,
    private val mapperResultErrorToErrorId: Mapper<Throwable?, Int>
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
            FolderEditorEvent.InputTextChanged -> onInputTextChangedEvent()
        }

    }

    private fun onInputTextChangedEvent() {
        updateUiState { inputError = null }
    }

    private suspend fun onLoadFolderEvent(folderId: Long) {
        updateUiState { isLoading = true }
        getFolderById(folderId = folderId)
            .onSuccess { folder ->
                updateUiState {
                    isLoading = false
                    this.folderId = folder.id
                    folderName = folder.name
                    folderIconUri = folder.iconUri
                }
            }
            .onFailure {
                updateUiState {
                    isLoading = false
                    inputError = mapperResultErrorToErrorId.map(from = it)
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
        newFolderNameValidator(folderName = name)
            .onSuccess {
                handleValidFolderData(name = name, iconUri = iconUri)
            }
            .onFailure {
                handleInvalidResult(errorStringId = mapperResultErrorToErrorId.map(it))
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


    private fun handleInvalidResult(@StringRes errorStringId: Int) {
        updateUiState {
            inputError = errorStringId
            isLoading = false
        }
    }

    private suspend fun handleValidFolderData(name: String, iconUri: String) {

        val addOrUpdateFolderResult =
            addOrUpdateFolder(folderId = stateValue.folderId, name = name, iconUri = iconUri)
        addOrUpdateFolderResult
            .onSuccess {
                onAddOrUpdateFolderResultSuccess(id = it)
            }.onFailure {
                onAddOrUpdateFolderResultFailure(resultError = it.cause)
            }

        updateUiState {
            isLoading = false
        }

    }

    private fun onAddOrUpdateFolderResultFailure(resultError: Throwable?) {


    }

    private suspend fun onAddOrUpdateFolderResultSuccess(id: Long) {
        stateValue.folderId?.let {
            FolderEditorOneTimeEvent.NavigateBack.processOneTimeEvent()
        } ?: FolderEditorOneTimeEvent.NavigateTo(
            NavigationRoute.DirectNotes(
                folderId = id
            )
        ).processOneTimeEvent()

    }

    private fun onFolderNameInputChangedEvent(folderName: String) {
        updateUiState {
            this.folderName = folderName
            inputError = null
        }
    }


}