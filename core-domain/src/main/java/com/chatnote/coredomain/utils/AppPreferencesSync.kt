package com.chatnote.coredomain.utils

interface AppPreferencesSync {
    suspend fun isFirstSession(): Boolean
    suspend fun isDefaultFoldersInitialized(): Boolean
    suspend fun markDefaultFoldersInitialized()
    suspend fun getAppSessionCount(): Int
    suspend fun incrementAppSessionCount()
    suspend fun markFolderOnboardingAsShown()
    suspend fun hasFolderOnboardingBeenShown(): Boolean
}
