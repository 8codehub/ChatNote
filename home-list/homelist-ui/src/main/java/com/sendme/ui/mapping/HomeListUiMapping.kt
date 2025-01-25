package com.sendme.ui.mapping

import com.pingpad.coredomain.mapper.Mapper
import com.pingpad.coreui.util.DateFormatter
import com.sendme.domain.model.Folder
import com.sendme.ui.model.UiFolder
import javax.inject.Inject

internal class FolderDomainToUiFolderMapper @Inject constructor(private val dateFormatter: DateFormatter) :
    Mapper<Folder, UiFolder> {

    override fun map(from: Folder): UiFolder {
        return UiFolder(
            id = from.id,
            name = from.name,
            createdAt = from.createdAt,
            lastNote = from.lastNote,
            iconUri = from.iconUri,
            lastNoteCreatedDate = from.lastNoteCreatedDate.takeIf { it != 0L }?.let {
                dateFormatter.formatShort(it)
            },
            isPinned = from.isPinned
        )
    }
}
