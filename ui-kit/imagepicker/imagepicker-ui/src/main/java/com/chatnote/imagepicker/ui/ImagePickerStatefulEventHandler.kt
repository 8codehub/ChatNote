package com.chatnote.imagepicker.ui

import android.content.Context
import android.net.Uri
import com.chatnote.coredomain.extension.getLastId
import com.chatnote.coreui.arch.StatefulEventHandler
import com.chatnote.imagepicker.ui.ImagePickerContract.ImagePickerEvent
import com.chatnote.imagepicker.ui.ImagePickerContract.ImagePickerOneTimeEvent
import com.chatnote.imagepicker.ui.ImagePickerContract.ImagePickerState
import com.chatnote.imagepicker.ui.ImagePickerContract.MutableImagePickerState
import com.chatnote.imagepicker.ui.model.AttachMode
import com.chatnote.imagepicker.ui.model.UiImageItem
import com.chatnote.imagepickerdomain.usecase.GenerateCameraUriUseCase
import com.chatnote.imagepickerdomain.usecase.GetRecentImagesUseCase
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ImagePickerStatefulEventHandler @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getRecentImagesUseCase: GetRecentImagesUseCase,
    private val generateCameraUriUseCase: GenerateCameraUriUseCase,
) : StatefulEventHandler<
        ImagePickerEvent,
        ImagePickerOneTimeEvent,
        ImagePickerState,
        MutableImagePickerState
        >(ImagePickerState()) {

    override suspend fun process(event: ImagePickerEvent, args: Any?) {
        when (event) {
            is ImagePickerEvent.StateReady -> onStateReady(mode = event.mode)
            is ImagePickerEvent.LoadImages -> onLoadImages()
            is ImagePickerEvent.ToggleImageSelection -> toggleSelection(event.uri)
            is ImagePickerEvent.AddGalleryImages -> onAddGalleryImages(event.uris)
            is ImagePickerEvent.CameraImageTaken -> onCameraImageTaken(event.uri)
            is ImagePickerEvent.OpenCamera -> openCamera()
            is ImagePickerEvent.OpenGallery -> openGallery()
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
                add(UiImageItem(uri, isSelected = true))
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

    private fun onCameraImageTaken(uri: Uri) {
        updateUiState {
            allImages =
                listOf(UiImageItem(uri, true)) + allImages.filterNot { it.uri == uri }
        }
    }

    private suspend fun openCamera() {
        val cameraUri = generateCameraUriUseCase()
        ImagePickerOneTimeEvent.OpenCamera(cameraUri).processOneTimeEvent()
    }

    private suspend fun openGallery() {
        ImagePickerOneTimeEvent.OpenGallery.processOneTimeEvent()
    }
}
