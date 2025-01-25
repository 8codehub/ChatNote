package com.pingpad.coredomain.facade

interface NotesRepositoryFacade {
    suspend fun deleteNotesByFolderId(folderId: Long): Result<Int>
}
