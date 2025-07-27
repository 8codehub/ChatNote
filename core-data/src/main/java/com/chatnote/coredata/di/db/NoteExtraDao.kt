package com.chatnote.coredata.di.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chatnote.coredata.di.model.NoteExtraEntity

@Dao
interface NoteExtraDao {

    @Query("SELECT * FROM note_extras WHERE note_id = :noteId")
    suspend fun getExtrasForNote(noteId: Long): List<NoteExtraEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExtras(extras: List<NoteExtraEntity>)

    @Query("DELETE FROM note_extras WHERE note_id = :noteId")
    suspend fun deleteExtrasForNote(noteId: Long)
}