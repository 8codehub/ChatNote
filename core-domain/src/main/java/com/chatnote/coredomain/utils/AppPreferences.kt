package com.chatnote.coredomain.utils

interface AppPreferences {

    suspend fun isFirstSession(): Boolean

    suspend fun getAppSessionCount(): Int

    suspend fun incrementAppSessionCount()

}