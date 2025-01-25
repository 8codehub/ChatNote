package com.pingpad.coredomain.bridge

interface NotesRepositoryFacade {
    suspend fun deleteNotesByFolderId(folderId: Long):Result<Int>
}