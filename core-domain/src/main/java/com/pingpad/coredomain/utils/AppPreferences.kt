package com.pingpad.coredomain.utils

interface AppPreferences {

    suspend fun isFirstOpen(): Boolean

    suspend fun getAppSessionCount(): Int

    suspend fun incrementAppSessionCount()

}