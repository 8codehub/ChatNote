package com.chatnote.coredata.di.util

import com.chatnote.coredomain.utils.AppPreferencesSync
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class AppPreferencesSyncImpl @Inject constructor(
    private val sharedPreferencesHelper: SharedPreferencesHelper
) : AppPreferencesSync {

    companion object {
        private const val KEY_APP_SESSION_COUNT = "app_session_count"
        private const val KEY_DEFAULT_FOLDERS_INITIALIZED = "is_default_folders_initialized"
        private const val FOLDER_ONBOARDING_SHOWN = "folder_onboarding_shown"
    }

    override suspend fun isFirstSession(): Boolean {
        return sharedPreferencesHelper.getBoolean(KEY_DEFAULT_FOLDERS_INITIALIZED, false)
    }

    override suspend fun isDefaultFoldersInitialized(): Boolean {
        return sharedPreferencesHelper.getBoolean(KEY_DEFAULT_FOLDERS_INITIALIZED, false)
    }

    override suspend fun markDefaultFoldersInitialized() {
        sharedPreferencesHelper.saveBoolean(KEY_DEFAULT_FOLDERS_INITIALIZED, true)
    }

    override suspend fun getAppSessionCount(): Int {
        return sharedPreferencesHelper.getInt(KEY_APP_SESSION_COUNT, 0)
    }

    override suspend fun incrementAppSessionCount() {
        val currentCount = getAppSessionCount()
        sharedPreferencesHelper.saveInt(KEY_APP_SESSION_COUNT, currentCount.plus(1))
    }

    override suspend fun hasFolderOnboardingBeenShown(): Boolean {
        return sharedPreferencesHelper.getBoolean(FOLDER_ONBOARDING_SHOWN, false)
    }

    override suspend fun markFolderOnboardingAsShown() {
        sharedPreferencesHelper.saveBoolean(FOLDER_ONBOARDING_SHOWN, true)
    }

}
