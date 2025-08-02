package com.chatnote.ui.model

import androidx.compose.runtime.Stable
import com.chatnote.coreui.model.TimeTag

@Stable
data class UiFolder(
    val id: Long? = null,
    val name: String,
    val lastNote: String,
    val iconUri: String? = null,
    val lastNoteCreatedDate: String?,
    val createdAt: Long,
    val isPinned: Boolean,
    val timeTag: TimeTag,
    val lastNoteExtras: List<UiNoteExtra>
)

sealed class UiNoteExtra {
    abstract val id: Long
    abstract val value: String

    data class Image(
        override val id: Long,
        override val value: String
    ) : UiNoteExtra()

    data class Voice(
        override val id: Long,
        override val value: String
    ) : UiNoteExtra()

    data class File(
        override val id: Long,
        override val value: String
    ) : UiNoteExtra()
}
