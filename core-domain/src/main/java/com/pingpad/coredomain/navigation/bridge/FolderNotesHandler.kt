package com.pingpad.coredomain.navigation.bridge

interface FolderNotesHandler {
    suspend fun deleteNotesByFolderId(folderId: Long):Result<Int>
}