package com.chatnote.directnotesdomain.usecase

import com.chatnote.coredomain.models.FolderBaseInfo
import com.chatnote.directnotesdomain.model.ActionableContent
import com.chatnote.directnotesdomain.model.Note
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

interface ExtractActionableContentUseCase {
    operator fun invoke(fullMessage: String): ActionableContent
}

interface DeleteNoteUseCase {
    suspend operator fun invoke(noteId: Long, folderId: Long): Result<Unit>
}