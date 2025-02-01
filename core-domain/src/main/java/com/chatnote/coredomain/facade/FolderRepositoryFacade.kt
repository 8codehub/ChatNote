package com.chatnote.coredomain.facade

interface FolderRepositoryFacade {

    suspend fun updateFolderWithLastNote(
        folderId: Long,
        lastNote: String,
        lastNoteDate: Long
    ): Result<Unit>
}
