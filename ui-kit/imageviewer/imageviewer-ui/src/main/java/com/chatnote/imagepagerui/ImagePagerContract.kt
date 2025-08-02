package com.chatnote.imagepagerui

import androidx.annotation.StringRes
import chatnote.coreui.R
import com.chatnote.coreui.arch.ConvertibleState
import com.chatnote.coreui.arch.MutableConvertibleState
import com.chatnote.coreui.arch.UiEvent
import com.chatnote.coreui.arch.UiOneTimeEvent

object ImagePagerContract {

    // Immutable State
    data class ImagePagerState(
        val images: List<String> = emptyList(),
        override val isLoading: Boolean = false,
        @StringRes override val generalError: Int = R.string.general_error
    ) : ConvertibleState<ImagePagerState, MutableImagePagerState> {
        override fun toMutable(): MutableImagePagerState {
            return MutableImagePagerState(
                images = images,
                isLoading = isLoading,
                generalError = generalError,
            )
        }
    }


    // Mutable State
    class MutableImagePagerState(
        var images: List<String> = emptyList(),
        override var isLoading: Boolean = false,
        @StringRes override var generalError: Int = chatnote.coreui.R.string.general_error
    ) : MutableConvertibleState<ImagePagerState> {
        override fun toImmutable(): ImagePagerState {
            return ImagePagerState(
                images = images,
                isLoading = isLoading,
                generalError = generalError,
            )
        }
    }

    // UI Events
    sealed class ImagePagerEvent : UiEvent {
        data class LoadImages(val defaultImage: String?, val images: List<String>) :
            ImagePagerEvent()

        data class GeneralError(val throwable: Throwable) : ImagePagerEvent()
    }

    // One-Time Events
    sealed class ImagePagerOneTimeEvent : UiOneTimeEvent {
        data class FailedOperation(@StringRes val error: Int) : ImagePagerOneTimeEvent()
    }
}
