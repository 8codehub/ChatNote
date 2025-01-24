package com.sendme.domain.usecase

import com.sendme.domain.model.Folder
import com.sendme.domain.model.FolderIcon
import kotlinx.coroutines.flow.Flow

interface GetFoldersUseCase {
    operator fun invoke(): Flow<List<Folder>>
}

interface GetFolderByIdUseCase {
    suspend operator fun invoke(folderId: Long): Result<Folder>
}

interface PinFolderUseCase {
    suspend operator fun invoke(folderId: Long): Result<Unit>
}

interface DeleteFolderUseCase {
    suspend operator fun invoke(folderId: Long): Result<Int>
}

interface UnpinFolderUseCase {
    suspend operator fun invoke(folderId: Long): Result<Unit>
}

interface AddOrUpdateFolderUseCase {
    suspend operator fun invoke(folderId: Long?, name: String, iconUri: String): Long
}

interface GetFolderIconsUseCase {
    operator fun invoke(): List<FolderIcon>
}