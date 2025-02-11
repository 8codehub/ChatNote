package com.chatnote.coredata.di.util

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

internal class SharedPreferencesHelperImpl @Inject constructor(
    @ApplicationContext context: Context
) : SharedPreferencesHelper {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("Preferences", Context.MODE_PRIVATE)

    override fun saveString(key: String, value: String): Boolean {
        return sharedPreferences.edit().putString(key, value).commit()
    }

    override fun getString(key: String, defaultValue: String): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }

    override fun saveInt(key: String, value: Int): Boolean {
        return sharedPreferences.edit().putInt(key, value).commit()
    }

    override fun getInt(key: String, defaultValue: Int): Int {
        return sharedPreferences.getInt(key, defaultValue)
    }

    override fun saveBoolean(key: String, value: Boolean): Boolean {
        return sharedPreferences.edit().putBoolean(key, value).commit()
    }

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    override fun remove(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }

    override fun clearAll() {
        sharedPreferences.edit().clear().apply()
    }
}