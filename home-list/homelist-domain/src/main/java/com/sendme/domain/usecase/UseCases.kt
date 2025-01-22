package com.sendme.domain.usecase

import com.sendme.domain.model.Folder
import com.sendme.domain.model.FolderIcon
import kotlinx.coroutines.flow.Flow

interface GetFoldersUseCase {
    operator fun invoke(): Flow<List<Folder>>
}

interface PinFolderUseCase {
    suspend operator fun invoke(folderId: Long): Result<Unit>
}

interface UnpinFolderUseCase {
    suspend operator fun invoke(folderId: Long): Result<Unit>
}

interface CreateFolderUseCase {
    suspend operator fun invoke(folderName: String, iconUri: String): Long
}

interface GetFolderIconsUseCase {
    operator fun invoke(): List<FolderIcon>
}