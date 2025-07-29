package com.chatnote.ui.mapping

import com.chatnote.coredomain.mapper.Mapper
import com.chatnote.coredomain.models.NoteExtra
import com.chatnote.coredomain.models.NoteExtraType
import com.chatnote.coreui.util.DateFormatter
import com.chatnote.domain.model.Folder
import com.chatnote.ui.model.UiFolder
import com.chatnote.ui.model.UiNoteExtra
import javax.inject.Inject

internal class FolderDomainToUiFolderMapper @Inject constructor(
    private val dateFormatter: DateFormatter,
    private val extraMapper: Mapper<NoteExtra, UiNoteExtra>
) :
    Mapper<Folder, UiFolder> {

    override fun map(from: Folder): UiFolder {
        return UiFolder(
            id = from.id,
            name = from.name,
            createdAt = from.createdAt,
            lastNote = from.lastNote,
            lastNoteExtras = extraMapper.mapList(from.lastNoteExtras),
            iconUri = from.iconUri,
            lastNoteCreatedDate = from.lastNoteCreatedDate.takeIf { it != 0L }?.let {
                dateFormatter.formatShort(it)
            },
            isPinned = from.isPinned
        )
    }
}


internal class NoteExtraToUiNoteExtraMapper @Inject constructor() : Mapper<NoteExtra, UiNoteExtra> {
    override fun map(from: NoteExtra): UiNoteExtra = when (from.type) {
        NoteExtraType.IMAGE -> UiNoteExtra.Image(from.id, from.value)
        NoteExtraType.VOICE -> UiNoteExtra.Voice(from.id, from.value)
        NoteExtraType.FILE -> UiNoteExtra.File(from.id, from.value)
    }
}
