package com.chatnote.coredata.di.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chatnote.coredata.di.model.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes WHERE folderId = :folderId ORDER BY note_last_created_at DESC")
    fun getNotesForFolder(folderId: Long): Flow<List<NoteEntity>>

    @Query(
        """
        SELECT * FROM notes 
        WHERE folderId = :folderId 
        ORDER BY note_last_created_at DESC 
        LIMIT 1
    """
    )
    fun getMostRecentNoteForFolder(folderId: Long): Flow<NoteEntity?>

    @Query("DELETE FROM notes WHERE folderId = :folderId")
    suspend fun deleteNotesByFolderId(folderId: Long): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(noteEntity: NoteEntity): Long

    @Delete
    suspend fun deleteNote(noteEntity: NoteEntity)

    @Query("DELETE FROM notes WHERE id = :noteId")
    suspend fun deleteNoteById(noteId: Long): Int
}
