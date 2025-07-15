package com.chatnote.imagepicker.ui.model

import android.net.Uri

data class UiImageItem(
    val uri: Uri,
    val isSelected: Boolean
)