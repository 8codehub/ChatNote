package com.pingpad.coredomain.navigation.bridge

interface NotesRepositoryFacade {
    suspend fun deleteNotesByFolderId(folderId: Long):Result<Int>
}