package com.sendme.data.mapping


import com.pingpad.coredomain.navigation.mapper.Mapper
import com.pingpad.coredomain.navigation.models.FolderBaseInfo
import com.sendme.data.models.FolderEntity
import com.sendme.domain.model.Folder
import javax.inject.Inject

internal class FolderEntityToFolderMapper @Inject constructor() : Mapper<FolderEntity, Folder> {
    override fun map(from: FolderEntity): Folder {
        return Folder(
            id = from.id,
            name = from.name,
            createdAt = from.createdAt,
            lastNote = from.lastNoteContent.orEmpty(),
            iconUri = from.iconUri,
            lastNoteCreatedDate = "Date",
            isPinned = from.pinnedDate > 0
        )
    }
}

internal class FolderEntityToFolderBaseInfoMapper @Inject constructor() :
    Mapper<FolderEntity, FolderBaseInfo> {
    override fun map(from: FolderEntity): FolderBaseInfo {
        return FolderBaseInfo(
            id = from.id ?: 0,
            name = from.name,
            iconUri = from.iconUri.orEmpty()
        )
    }
}

internal class FolderToFolderEntityMapper @Inject constructor() : Mapper<Folder, FolderEntity> {
    override fun map(from: Folder): FolderEntity {
        return FolderEntity(
            id = from.id,
            name = from.name,
            createdAt = from.createdAt,
            iconUri = from.iconUri,
            lastNoteCreatedAt = System.currentTimeMillis(),
            lastNoteContent = from.lastNote,
            pinnedDate = 0L
        )
    }
}
