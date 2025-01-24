package com.sendme.data.db

import androidx.room.*
import com.sendme.data.models.FolderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FolderDao {
    @Query(
        """
    SELECT * FROM folders
    ORDER BY 
        CASE WHEN pinnedDate > 0 THEN 0 ELSE 1 END,
        pinnedDate DESC,
        createdAt DESC 
"""
    )
    fun getAllFolders(): Flow<List<FolderEntity>>


    @Query("SELECT * FROM folders WHERE id = :folderId LIMIT 1;\n")
    suspend fun getFolderById(folderId: Long): FolderEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplaceFolder(folderEntity: FolderEntity): Long

    @Delete
    suspend fun deleteFolder(folderEntity: FolderEntity)

    @Query("UPDATE folders SET lastNoteContent = :lastNote, lastNoteCreatedAt = :lastNoteDate WHERE id = :folderId")
    suspend fun updateFolderLastNote(folderId: Long, lastNote: String, lastNoteDate: Long)

    @Query("UPDATE folders SET pinnedDate = :pinnedDate WHERE id = :folderId")
    suspend fun pinFolder(folderId: Long, pinnedDate: Long = System.currentTimeMillis()): Int

    @Query("UPDATE folders SET pinnedDate = 0 WHERE id = :folderId")
    suspend fun unpinFolder(folderId: Long): Int
}