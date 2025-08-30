package com.chatnote.coredomain.models

data class NoteExtra(
    val id: Long = 0,
    val type: NoteExtraType,
    val value: String
)

enum class NoteExtraType {
    IMAGE, VOICE, FILE
}
