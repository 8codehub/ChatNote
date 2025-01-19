package com.sendme.data.db

import androidx.room.*
import com.sendme.data.models.FolderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FolderDao {
    @Query("SELECT * FROM folders ORDER BY createdAt DESC")
    fun getAllFolders(): Flow<List<FolderEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFolder(folderEntity: FolderEntity): Long

    @Delete
    suspend fun deleteFolder(folderEntity: FolderEntity)

    @Query("UPDATE folders SET lastNoteContent = :lastNote, lastNoteCreatedAt = :lastNoteDate WHERE id = :folderId")
    suspend fun updateFolderLastNote(folderId: Int, lastNote: String, lastNoteDate: Long)

}