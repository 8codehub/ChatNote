package com.sendme.ui.editor.state

import androidx.annotation.StringRes


data class CreateNewFolderState(
    val folderName:String = "",
    val icons: List<String> = emptyList(),
    val isLoading: Boolean = false,
    @StringRes val inputError: Int? = null
)