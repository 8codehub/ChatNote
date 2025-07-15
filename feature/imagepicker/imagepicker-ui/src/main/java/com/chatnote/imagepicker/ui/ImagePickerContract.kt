package com.chatnote.imagepicker.ui

import android.net.Uri
import androidx.annotation.StringRes
import com.chatnote.coreui.arch.ConvertibleState
import com.chatnote.coreui.arch.MutableConvertibleState
import com.chatnote.coreui.arch.UiEvent
import com.chatnote.coreui.arch.UiOneTimeEvent
import com.chatnote.imagepicker.ui.model.AttachMode
import com.chatnote.imagepicker.ui.model.UiImageItem
import  chatnote.coreui.R as CR

object ImagePickerContract {

    const val MAX_SELECTION_COUNT: Int = 3

    data class ImagePickerState(
        val allImages: List<UiImageItem> = emptyList(),
        val attachMode: AttachMode = AttachMode.MultiAttach,
        override val isLoading: Boolean = false,
        @StringRes override val generalError: Int = CR.string.general_error
    ) : ConvertibleState<ImagePickerState, MutableImagePickerState> {
        override fun toMutable(): MutableImagePickerState {
            return MutableImagePickerState(
                allImages = allImages,
                isLoading = isLoading,
                attachMode = attachMode,
                generalError = generalError
            )
        }
    }

    class MutableImagePickerState(
        var allImages: List<UiImageItem> = emptyList(),
        override var isLoading: Boolean = false,
        var attachMode: AttachMode = AttachMode.MultiAttach,
        @StringRes override var generalError: Int = CR.string.general_error
    ) : MutableConvertibleState<ImagePickerState> {
        override fun toImmutable(): ImagePickerState {
            return ImagePickerState(
                allImages = allImages,
                isLoading = isLoading,
                attachMode = attachMode,
                generalError = generalError
            )
        }
    }

    sealed class ImagePickerEvent : UiEvent {
        data class StateReady(val mode: AttachMode) : ImagePickerEvent()
        data object LoadImages : ImagePickerEvent()
        data class ToggleImageSelection(val uri: Uri) : ImagePickerEvent()
        data class AddGalleryImages(val uris: List<Uri>) : ImagePickerEvent()
        data object OpenCamera : ImagePickerEvent()
        data object OpenGallery : ImagePickerEvent()
        data class CameraImageTaken(val uri: Uri) : ImagePickerEvent()
        data object AttachSelectedImages : ImagePickerEvent()

    }

    sealed class ImagePickerOneTimeEvent : UiOneTimeEvent {
        data class OpenCamera(val uri: Uri) : ImagePickerOneTimeEvent()
        data class ImagesSelected(val uris: List<Uri>) : ImagePickerOneTimeEvent()
        data object OpenGallery : ImagePickerOneTimeEvent()
    }
}
