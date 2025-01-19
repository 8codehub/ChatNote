package com.sendme.domain.model


data class Folder(
    val id: Long = -1,
    val name: String,
    val lastNote: String,
    val iconUri: String?,
    val lastNoteCreatedDate: String,
    val createdAt: Long
)