package com.sendme.directnotesui.mapper

import com.pingpad.coredomain.mapper.Mapper
import com.pingpad.coreui.util.DateFormatter
import com.sendme.directnotesui.model.UiNote
import com.sendme.directnotsdomain.model.Note
import javax.inject.Inject

class NotesToUiNotesMapper @Inject constructor(private val dateFormatter: DateFormatter) :
    Mapper<Note, UiNote> {
    override fun map(from: Note) = UiNote(
        id = from.id,
        content = from.content,
        date = dateFormatter.formatLong(millis = from.createdAt)
    )
}