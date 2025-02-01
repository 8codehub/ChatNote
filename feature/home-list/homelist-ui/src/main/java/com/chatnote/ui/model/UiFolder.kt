package com.chatnote.ui.model

import androidx.compose.runtime.Stable

@Stable
data class UiFolder(
    val id: Long? = null,
    val name: String,
    val lastNote: String,
    val iconUri: String? = null,
    val lastNoteCreatedDate: String?,
    val createdAt: Long,
    val isPinned: Boolean
)
