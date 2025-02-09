package com.chatnote.domain.model

data class Folder(
    val id: Long? = null,
    val iconUri: String? = null,
    val name: String,
    val createdAt: Long,
    val lastNoteId: Long,
    val lastNote: String,
    val isPinned: Boolean,
    val lastNoteCreatedDate: Long,
)
