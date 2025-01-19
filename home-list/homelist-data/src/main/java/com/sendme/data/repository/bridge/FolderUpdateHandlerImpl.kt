package com.sendme.data.repository.bridge

import com.sendme.coredomain.navigation.bridge.FolderUpdateHandler
import com.sendme.data.db.FolderDao
import javax.inject.Inject

class FolderUpdateHandlerImpl @Inject constructor(
    private val folderDao: FolderDao
) : FolderUpdateHandler {
    override suspend fun updateFolderWithLastNote(folderId: Int, lastNote: String, lastNoteDate: Long) {
        folderDao.updateFolderLastNote(folderId = folderId, lastNote = lastNote, lastNoteDate = lastNoteDate)
    }
}