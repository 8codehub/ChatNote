package com.sendme.directnotsdomain.usecase

import com.pingpad.coredomain.models.FolderBaseInfo
import com.sendme.directnotsdomain.model.Note
import kotlinx.coroutines.flow.Flow

interface GetNotesUseCase {
    operator fun invoke(folderId: Long): Flow<List<Note>>
}

interface AddNoteUseCase {
    suspend operator fun invoke(folderId: Long, note: Note): Result<Unit>
}

interface ObserveFolderUseCase {
    operator fun invoke(folderId: Long): Flow<FolderBaseInfo?>
}