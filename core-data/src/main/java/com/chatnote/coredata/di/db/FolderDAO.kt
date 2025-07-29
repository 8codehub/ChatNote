package com.chatnote.coredata.di.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.chatnote.coredata.di.model.FolderEntity
import com.chatnote.coredata.di.model.FolderWithLastNote
import kotlinx.coroutines.flow.Flow

@Dao
interface FolderDao {

    @Transaction
    @Query(
        """
    SELECT f.*, 
           (SELECT n.note_last_content FROM notes n 
            WHERE n.folderId = f.id 
            ORDER BY n.note_last_created_at DESC 
            LIMIT 1) AS note_last_content,
           (SELECT n.note_last_created_at FROM notes n 
            WHERE n.folderId = f.id 
            ORDER BY n.note_last_created_at DESC 
            LIMIT 1) AS note_last_created_at,
           (SELECT n.id FROM notes n 
            WHERE n.folderId = f.id 
            ORDER BY n.note_last_created_at DESC 
            LIMIT 1) AS note_last_id
    FROM folders f
    ORDER BY 
        CASE 
            WHEN f.pinnedDate > 0 THEN 0 
            ELSE 1 
        END, 
        COALESCE((SELECT MAX(n.note_last_created_at) FROM notes n WHERE n.folderId = f.id), f.createdAt) DESC
    """
    )
    fun observeFoldersWithLastNote(): Flow<List<FolderWithLastNote>>

    @Query("SELECT * FROM folders WHERE id = :folderId LIMIT 1")
    fun observeFolderById(folderId: Long): Flow<FolderEntity>

    @Query("SELECT * FROM folders WHERE id = :folderId LIMIT 1")
    suspend fun getFolderById(folderId: Long): FolderEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplaceFolder(folderEntity: FolderEntity): Long

    @Delete
    suspend fun deleteFolder(folderEntity: FolderEntity)

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
