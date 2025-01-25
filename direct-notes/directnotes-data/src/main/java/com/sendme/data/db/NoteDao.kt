package com.sendme.data.db

import androidx.room.*
import com.sendme.data.model.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes WHERE folderId = :folderId ORDER BY createdAt DESC")
    fun getNotesForFolder(folderId: Long): Flow<List<NoteEntity>>

    @Query("DELETE FROM notes WHERE folderId = :folderId")
    suspend fun deleteNotesByFolderId(folderId: Long): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(noteEntity: NoteEntity): Long

    @Delete
    suspend fun deleteNote(noteEntity: NoteEntity)
}
