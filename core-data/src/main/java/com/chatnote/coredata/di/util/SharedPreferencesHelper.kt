package com.chatnote.coredata.di.util

interface SharedPreferencesHelper {
    fun clearAll()
    fun remove(key: String)
    fun saveInt(key: String, value: Int)
    fun saveString(key: String, value: String)
    fun saveBoolean(key: String, value: Boolean)
    fun getInt(key: String, defaultValue: Int = 0): Int
    fun getString(key: String, defaultValue: String = ""): String
    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean
}