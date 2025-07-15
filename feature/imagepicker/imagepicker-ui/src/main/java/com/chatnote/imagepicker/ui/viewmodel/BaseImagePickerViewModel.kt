package com.chatnote.imagepicker.ui.viewmodel

import android.net.Uri
import com.chatnote.coreui.arch.EventDrivenViewModel
import com.chatnote.coreui.arch.StatefulEventHandler
import com.chatnote.imagepicker.ui.ImagePickerContract
import com.chatnote.imagepicker.ui.model.AttachMode
import kotlinx.coroutines.CoroutineDispatcher

abstract class BaseImagePickerViewModel(
    dispatcher: CoroutineDispatcher,
    statefulEventHandler: StatefulEventHandler<
            ImagePickerContract.ImagePickerEvent,
            ImagePickerContract.ImagePickerOneTimeEvent,
            ImagePickerContract.ImagePickerState,
            ImagePickerContract.MutableImagePickerState
            >
) : EventDrivenViewModel<
        ImagePickerContract.ImagePickerState,
        ImagePickerContract.MutableImagePickerState,
        ImagePickerContract.ImagePickerEvent,
        ImagePickerContract.ImagePickerOneTimeEvent,
        StatefulEventHandler<
                ImagePickerContract.ImagePickerEvent,
                ImagePickerContract.ImagePickerOneTimeEvent,
                ImagePickerContract.ImagePickerState,
                ImagePickerContract.MutableImagePickerState
                >
        >(statefulEventHandler = statefulEventHandler, ioDispatcher = dispatcher) {

    protected abstract val attachMode: AttachMode

    override fun onStateReady() {
        ImagePickerContract.ImagePickerEvent.StateReady(mode = attachMode).processWithLaunch()
    }

    fun loadImages() {
        ImagePickerContract.ImagePickerEvent.LoadImages.processWithLaunch()
    }

    fun toggleImageSelection(uri: Uri) {
        ImagePickerContract.ImagePickerEvent.ToggleImageSelection(uri).processWithLaunch()
    }

    fun addGalleryImages(uris: List<Uri>) {
        ImagePickerContract.ImagePickerEvent.AddGalleryImages(uris).processWithLaunch()
    }

    fun onCameraImageTaken(uri: Uri) {
        ImagePickerContract.ImagePickerEvent.CameraImageTaken(uri).processWithLaunch()
    }

    fun attachSelectedImages() {
        ImagePickerContract.ImagePickerEvent.AttachSelectedImages.processWithLaunch()
    }

    fun openCamera() {
        ImagePickerContract.ImagePickerEvent.OpenCamera.processWithLaunch()
    }

    fun openGallery() {
        ImagePickerContract.ImagePickerEvent.OpenGallery.processWithLaunch()
    }

    override fun onGeneralError(throwable: Throwable) {}
}