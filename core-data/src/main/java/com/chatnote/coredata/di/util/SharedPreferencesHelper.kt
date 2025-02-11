package com.chatnote.coredata.di.util

interface SharedPreferencesHelper {
    fun clearAll()
    fun remove(key: String)
    fun saveInt(key: String, value: Int): Boolean
    fun saveString(key: String, value: String): Boolean
    fun saveBoolean(key: String, value: Boolean): Boolean
    fun getInt(key: String, defaultValue: Int = 0): Int
    fun getString(key: String, defaultValue: String = ""): String
    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean
}