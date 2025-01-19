package com.sendme.ui.provider

import android.content.Context
import javax.inject.Inject
import dagger.hilt.android.scopes.ViewModelScoped

@ViewModelScoped
class ImageUrlProvider @Inject constructor(
    private val context: Context
) {
    fun getAllIcons(): List<String> {
        return try {
            context.assets.list("icons")?.toList() ?: emptyList()
        } catch (e: Exception) {
            emptyList() // Handle errors gracefully
        }
    }
}
