package com.sendme.directnotsdomain.usecase

import com.pingpad.coredomain.models.FolderBaseInfo
import kotlinx.coroutines.flow.Flow
import com.sendme.directnotsdomain.SendMeNote

interface GetNotesUseCase {
    operator fun invoke(folderId: Long): Flow<List<SendMeNote>>
}

interface AddNoteUseCase {
    suspend operator fun invoke(folderId: Long, note: SendMeNote):Result<Unit>
}

interface ObserveFolderUseCase {
    operator fun invoke(folderId: Long): Flow<FolderBaseInfo?>
}