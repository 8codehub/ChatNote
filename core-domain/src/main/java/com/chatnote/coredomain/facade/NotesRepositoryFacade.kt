package com.chatnote.coredomain.facade

interface NotesRepositoryFacade {

    suspend fun deleteNotesByFolderId(folderId: Long): Result<Int>
}
