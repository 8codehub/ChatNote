package com.chatnote.imagepicker.ui

import android.content.Context
import android.net.Uri
import com.chatnote.coredomain.extension.getLastId
import com.chatnote.coreui.arch.StatefulEventHandler
import com.chatnote.imagepicker.ui.ImagePickerContract.MAX_SELECTION_COUNT
import com.chatnote.imagepicker.ui.model.AttachMode
import com.chatnote.imagepicker.ui.model.UiImageItem
import com.chatnote.imagepickerdomain.usecase.CopyImageToAppStorage
import com.chatnote.imagepickerdomain.usecase.GenerateCameraUriUseCase
import com.chatnote.imagepickerdomain.usecase.GetRecentImagesUseCase
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ImagePickerStatefulEventHandler @Inject constructor(
    @ApplicationContext private val context: Context,
    private val copyImageToAppStorage: CopyImageToAppStorage,
    private val getRecentImagesUseCase: GetRecentImagesUseCase,
    private val generateCameraUriUseCase: GenerateCameraUriUseCase,
) : StatefulEventHandler<
        ImagePickerContract.ImagePickerEvent,
        ImagePickerContract.ImagePickerOneTimeEvent,
        ImagePickerContract.ImagePickerState,
        ImagePickerContract.MutableImagePickerState
        >(ImagePickerContract.ImagePickerState()) {

    override suspend fun process(event: ImagePickerContract.ImagePickerEvent, args: Any?) {
        when (event) {
            is ImagePickerContract.ImagePickerEvent.StateReady -> onStateReady(mode = event.mode)
            is ImagePickerContract.ImagePickerEvent.LoadImages -> onLoadImages()
            is ImagePickerContract.ImagePickerEvent.ToggleImageSelection -> toggleSelection(event.uri)
            is ImagePickerContract.ImagePickerEvent.AddGalleryImages -> onAddGalleryImages(event.uris)
            is ImagePickerContract.ImagePickerEvent.CameraImageTaken -> onCameraImageTaken(event.uri)
            is ImagePickerContract.ImagePickerEvent.AttachSelectedImages -> onAttachSelectedImages()
            is ImagePickerContract.ImagePickerEvent.OpenCamera -> openCamera()
            is ImagePickerContract.ImagePickerEvent.OpenGallery -> openGallery()
        }
    }

    private fun onStateReady(mode: AttachMode) {
        updateUiState {
            attachMode = mode
        }
    }

    private suspend fun onLoadImages() {
        if (stateValue.allImages.isEmpty()) {
            val images = getRecentImagesUseCase()
            updateUiState {
                allImages = images.map { UiImageItem(it, false) }
            }
        }
    }

    private suspend fun toggleSelection(uri: Uri) {
        updateUiState {
            val current = allImages.find { it.uri == uri } ?: return@updateUiState

            val currentMode = attachMode
            val selectedCount = allImages.count { it.isSelected }

            if (!current.isSelected && currentMode != AttachMode.SingleAttach && selectedCount >= MAX_SELECTION_COUNT) {
                return@updateUiState
            }

            allImages = if (currentMode == AttachMode.SingleAttach) {
                allImages.map { it.copy(isSelected = it.uri == uri) }
            } else {
                allImages.map {
                    if (it.uri == uri) it.copy(isSelected = !it.isSelected) else it
                }
            }
        }
    }

    private suspend fun onAddGalleryImages(uris: List<Uri>) {
        when (stateValue.attachMode) {
            AttachMode.SingleAttach -> handleSingleAttach(uris.firstOrNull())
            AttachMode.MultiAttach -> handleMultiAttach(uris)
        }
    }

    private suspend fun handleSingleAttach(uri: Uri?) {
        if (uri == null) return

        updateUiState {
            allImages = buildList {
                // Add selected image first
                add(UiImageItem(uri, isSelected = true))
                // Then add the rest (unselected and non-duplicates)
                addAll(
                    allImages.filterNot { it.uri == uri }
                        .map { it.copy(isSelected = false) }
                )
            }
        }
    }

    private suspend fun handleMultiAttach(uris: List<Uri>) {
        val addedIds = uris.mapNotNull { it.getLastId() }.toSet()

        updateUiState {
            val updated = allImages.map { image ->
                val id = image.uri.getLastId()
                if (id != null && id in addedIds) image.copy(isSelected = true) else image
            }

            val existingIds = updated.mapNotNull { it.uri.getLastId() }.toSet()

            val newItems = uris
                .filter { it.getLastId()?.let { id -> id !in existingIds } == true }
                .map { UiImageItem(it, true) }

            allImages = newItems + updated
        }
    }

    private suspend fun onAttachSelectedImages() {
        val selected = stateValue.allImages.filter { it.isSelected }
        val copied = selected.mapNotNull { copyImageToAppStorage(context, it.uri) }
        // Emit copied URIs
        ImagePickerContract.ImagePickerOneTimeEvent.ImagesSelected(copied).processOneTimeEvent()

    }

    private suspend fun onCameraImageTaken(uri: Uri) {
        uri?.let {
            updateUiState {
                allImages =
                    listOf(UiImageItem(it, true)) + allImages.filterNot { img -> img.uri == it }
            }
        }
    }

    private suspend fun openCamera() {
        val cameraUri = generateCameraUriUseCase()
        ImagePickerContract.ImagePickerOneTimeEvent.OpenCamera(cameraUri).processOneTimeEvent()
    }

    private suspend fun openGallery() {
        ImagePickerContract.ImagePickerOneTimeEvent.OpenGallery.processOneTimeEvent()
    }
}
