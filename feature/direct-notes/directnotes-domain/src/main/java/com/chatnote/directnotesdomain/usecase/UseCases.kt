package com.chatnote.directnotesdomain.usecase

import com.chatnote.coredomain.models.FolderBaseInfo
import com.chatnote.directnotesdomain.model.Note
import com.chatnote.directnotesdomain.model.NoteActionableContent
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
    operator fun invoke(fullMessage: String): NoteActionableContent
}

interface DeleteNoteUseCase {
    suspend operator fun invoke(noteId: Long): Result<Unit>
}