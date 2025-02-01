package com.chatnote.directnotesdomain.model

data class Note(
    val id: Long,
    val folderId: Long,
    val content: String,
    val createdAt: Long
)