package com.chatnote.coredata.di.db.convertor

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun fromListToString(list: List<String>): String {
        return list.joinToString("||")
    }

    @TypeConverter
    fun fromStringToList(data: String): List<String> {
        return if (data.isEmpty()) emptyList() else data.split("||")
    }
}