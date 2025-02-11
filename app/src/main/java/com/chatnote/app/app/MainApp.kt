package com.chatnote.app.app

import android.app.Application
import com.chatnote.coredomain.utils.AppPreferencesSync
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class MainApp : Application() {

    @Inject
    lateinit var appPreferences: AppPreferencesSync

    @Inject
    lateinit var applicationScope: CoroutineScope

    override fun onCreate() {
        super.onCreate()

        applicationScope.launch {
            appPreferences.incrementAppSessionCount()
        }
    }
}