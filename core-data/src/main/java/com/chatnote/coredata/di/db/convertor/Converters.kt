package com.chatnote.coredata.di.db.convertor

import androidx.room.TypeConverter
import com.chatnote.coredata.di.model.NoteEntityExtraType

class NoteExtraTypeConverter {

    @TypeConverter
    fun fromType(type: NoteEntityExtraType): Int = type.id

    @TypeConverter
    fun toType(id: Int): NoteEntityExtraType =
        NoteEntityExtraType.fromId(id) ?: NoteEntityExtraType.IMAGE
}