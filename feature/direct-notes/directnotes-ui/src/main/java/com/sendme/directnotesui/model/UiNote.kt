package com.sendme.directnotesui.model

import androidx.compose.runtime.Stable

@Stable
data class UiNote(
    val id: Long,
    val content: String,
    val date: String
)