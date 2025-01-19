package com.sendme.coredomain.navigation.bridge

interface FolderUpdateHandler {
    suspend fun updateFolderWithLastNote(folderId: Int, lastNote: String, lastNoteDate: Long)
}
