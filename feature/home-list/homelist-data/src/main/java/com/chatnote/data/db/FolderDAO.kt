package com.chatnote.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chatnote.data.models.FolderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FolderDao {

    @Query(
        """
    SELECT * FROM folders
    ORDER BY 
        CASE 
            WHEN pinnedDate > 0 THEN 0
            ELSE 1
        END, 
        CASE 
            WHEN lastNoteCreatedAt != 0 THEN lastNoteCreatedAt
            ELSE pinnedDate
        END DESC,
        CASE 
            WHEN lastNoteCreatedAt != 0 THEN lastNoteCreatedAt
            ELSE createdAt
        END DESC
    """
    )
    fun observeAllFolders(): Flow<List<FolderEntity>>


    @Query("SELECT * FROM folders WHERE id = :folderId LIMIT 1")
    fun observeFolderById(folderId: Long): Flow<FolderEntity>

    @Query("SELECT * FROM folders WHERE id = :folderId LIMIT 1")
    suspend fun getFolderById(folderId: Long): FolderEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplaceFolder(folderEntity: FolderEntity): Long

    @Delete
    suspend fun deleteFolder(folderEntity: FolderEntity)

    @Query(
        """
    UPDATE folders 
    SET lastNoteContent = :lastNote, 
        lastNoteCreatedAt = :lastNoteDate, 
        lastNoteId = :lastNoteId
    WHERE id = :folderId
    """
    )
    suspend fun updateFolderLastNote(
        folderId: Long,
        lastNote: String,
        lastNoteId: Long,
        lastNoteDate: Long
    )

    @Query(
        """
    UPDATE folders 
    SET lastNoteContent = '', lastNoteCreatedAt = 0
    WHERE id = :folderId AND lastNoteId = :noteId
    """
    )
    suspend fun clearFolderLastNoteIfMatch(folderId: Long, noteId: Long): Int

    @Query("UPDATE folders SET pinnedDate = :pinnedDate WHERE id = :folderId")
    suspend fun pinFolder(
        folderId: Long,
        pinnedDate: Long = System.currentTimeMillis()
    ): Int

    @Query("UPDATE folders SET pinnedDate = 0 WHERE id = :folderId")
    suspend fun unpinFolder(folderId: Long): Int

    @Query("DELETE FROM folders WHERE id = :folderId")
    suspend fun deleteFolder(folderId: Long): Int
}
