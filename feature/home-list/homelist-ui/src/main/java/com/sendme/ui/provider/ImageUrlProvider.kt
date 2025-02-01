package com.sendme.ui.provider

import android.content.Context
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class ImageUrlProvider @Inject constructor(
    private val context: Context
) {
    fun getAllIcons(): List<String> {
        return try {
            context.assets.list("icons")?.toList() ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }
}
