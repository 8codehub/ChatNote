package com.chatnote.domain.model

data class Folder(
    val id: Long? = null,
    val name: String,
    val lastNote: String,
    val iconUri: String? = null,
    val lastNoteCreatedDate: Long,
    val lastNoteId: Long,
    val createdAt: Long,
    val isPinned: Boolean
)
