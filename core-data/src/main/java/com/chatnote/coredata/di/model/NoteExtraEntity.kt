package com.chatnote.coredata.di.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "note_extras",
    foreignKeys = [ForeignKey(
        entity = NoteEntity::class,
        parentColumns = ["id"],
        childColumns = ["note_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["note_id"])] // ✅ Add this line
)
data class NoteExtraEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "note_id") val noteId: Long,
    val type: NoteEntityExtraType,
    val value: String
)

enum class NoteEntityExtraType(val id: Int, val typeName: String) {
    IMAGE(1, "Image"),
    VOICE(2, "Voice"),
    FILE(3, "File");

    companion object {
        fun fromId(id: Int): NoteEntityExtraType? = entries.find { it.id == id }

        fun fromName(name: String): NoteEntityExtraType? =
            entries.find { it.name.equals(name, ignoreCase = true) }
    }
}