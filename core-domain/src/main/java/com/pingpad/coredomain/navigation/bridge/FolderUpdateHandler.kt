package com.pingpad.coredomain.navigation.bridge

interface FolderUpdateHandler {
    suspend fun updateFolderWithLastNote(folderId: Long, lastNote: String, lastNoteDate: Long)
}
