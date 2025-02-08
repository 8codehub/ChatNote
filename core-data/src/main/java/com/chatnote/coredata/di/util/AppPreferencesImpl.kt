package com.chatnote.coredata.di.util

import com.chatnote.coredomain.utils.AppPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppPreferencesImpl @Inject constructor(
    private val sharedPreferencesHelper: SharedPreferencesHelper
) : AppPreferences {

    companion object {
        private const val KEY_APP_SESSION_COUNT = "app_session_count"
    }

    override suspend fun isFirstSession(): Boolean {
        return getAppSessionCount() <= 1
    }

    override suspend fun getAppSessionCount(): Int {
        return sharedPreferencesHelper.getInt(KEY_APP_SESSION_COUNT, 0)
    }

    override suspend fun incrementAppSessionCount() {
        val currentCount = getAppSessionCount()
        sharedPreferencesHelper.saveInt(KEY_APP_SESSION_COUNT, currentCount.plus(1))
    }
}
