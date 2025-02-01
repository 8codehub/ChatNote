package com.chatnote.coredomain.utils

interface AppPreferences {

    suspend fun isFirstOpen(): Boolean

    suspend fun getAppSessionCount(): Int

    suspend fun incrementAppSessionCount()

}