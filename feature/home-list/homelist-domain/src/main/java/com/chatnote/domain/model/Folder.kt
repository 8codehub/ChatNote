package com.chatnote.domain.model

import com.chatnote.coredomain.models.NoteExtra

data class Folder(
    val id: Long? = null,
    val iconUri: String? = null,
    val name: String,
    val createdAt: Long,
    val lastNoteId: Long,
    val lastNote: String,
    val isPinned: Boolean,
    val lastNoteCreatedDate: Long,
    val lastNoteExtras: List<NoteExtra>,
)
