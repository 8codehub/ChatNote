package com.chatnote.directnotesdomain.model

import com.chatnote.coredomain.models.NoteExtra

data class Note(
    val id: Long,
    val content: String,
    val folderId: Long,
    val createdAt: Long,
    val extras: List<NoteExtra>
)