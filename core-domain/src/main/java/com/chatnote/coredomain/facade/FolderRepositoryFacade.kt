package com.chatnote.coredomain.facade

interface FolderRepositoryFacade {

    suspend fun updateFolderWithLastNote(
        folderId: Long,
        lastNote: String,
        lastNoteId: Long,
        lastNoteDate: Long
    ): Result<Unit>

    suspend fun deleteFolderLastNoteIfMatch(folderId: Long, noteId: Long): Result<Unit>
}
