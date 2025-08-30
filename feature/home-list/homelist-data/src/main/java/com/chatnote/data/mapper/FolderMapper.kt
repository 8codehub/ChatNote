package com.chatnote.data.mapper

import com.chatnote.coredata.di.model.FolderEntity
import com.chatnote.coredata.di.model.FolderWithLastNote
import com.chatnote.coredata.di.model.NoteExtraEntity
import com.chatnote.coredomain.mapper.Mapper
import com.chatnote.coredomain.models.FolderBaseInfo
import com.chatnote.coredomain.models.NoteExtra
import com.chatnote.domain.model.Folder
import javax.inject.Inject

internal class FolderWithLastNoteToFolderMapper @Inject constructor(
    private val extraMapper: Mapper<NoteExtraEntity, NoteExtra>
) :
    Mapper<FolderWithLastNote, Folder> {
    override fun map(from: FolderWithLastNote): Folder = Folder(
        id = from.folder.id,
        name = from.folder.name,
        createdAt = from.folder.createdAt,
        lastNote = from.lastNoteContent.orEmpty(),
        lastNoteExtras = extraMapper.mapList(from.extras.orEmpty()),
        iconUri = from.folder.iconUri,
        lastNoteCreatedDate = from.lastNoteCreatedAt ?: 0,
        lastNoteId = from.lastNoteId ?: 0,
        isPinned = from.folder.pinnedDate > 0
    )
}

internal class FolderEntityToFolderMapper @Inject constructor() : Mapper<FolderEntity, Folder> {
    override fun map(from: FolderEntity): Folder = Folder(
        id = from.id,
        iconUri = from.iconUri,
        name = from.name,
        createdAt = from.createdAt,
        lastNoteId = 0,
        lastNote = "",
        isPinned = from.pinnedDate > 0,
        lastNoteCreatedDate = 0,
        lastNoteExtras = emptyList()
    )
}

internal class FolderEntityToFolderBaseInfoMapper @Inject constructor() :
    Mapper<FolderEntity, FolderBaseInfo> {
    override fun map(from: FolderEntity): FolderBaseInfo = FolderBaseInfo(
        id = from.id ?: 0,
        name = from.name,
        iconUri = from.iconUri.orEmpty()
    )
}

internal class FolderToFolderEntityMapper @Inject constructor() : Mapper<Folder, FolderEntity> {
    override fun map(from: Folder): FolderEntity = FolderEntity(
        id = from.id,
        name = from.name,
        createdAt = from.createdAt,
        iconUri = from.iconUri,
        pinnedDate = 0L
    )
}
