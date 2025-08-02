package com.chatnote.directnotesui.model

import androidx.compose.runtime.Stable
import com.chatnote.coreui.model.TimeTag
import java.util.Calendar

@Stable
data class UiNote(
    val id: Long,
    val content: String,
    val date: String,
    val imagePaths: List<String>,
    val timeTag: TimeTag
)

