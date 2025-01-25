package com.sendme.ui.model

data class UiFolder(
    val id: Long? = null,
    val name: String,
    val lastNote: String,
    val iconUri: String? = null,
    val lastNoteCreatedDate: String?,
    val createdAt: Long,
    val isPinned: Boolean
)
