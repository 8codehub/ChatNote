package com.chatnote.imagepicker.ui.model

enum class AttachMode {
    SingleAttach,
    MultiAttach;

    companion object {
        fun fromOrdinal(ordinal: Int?): AttachMode {
            return entries.getOrNull(ordinal ?: -1) ?: MultiAttach
        }
    }
}