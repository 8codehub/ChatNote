package com.chatnote.directnotesui.mapper

import com.chatnote.coredomain.mapper.Mapper
import com.chatnote.coreui.util.DateFormatter
import com.chatnote.directnotesui.model.UiNote
import com.chatnote.directnotesdomain.model.Note
import javax.inject.Inject

class NotesToUiNotesMapper @Inject constructor(private val dateFormatter: DateFormatter) :
    Mapper<Note, UiNote> {
    override fun map(from: Note) = UiNote(
        id = from.id,
        content = from.content,
        date = dateFormatter.formatLong(millis = from.createdAt)
    )
}