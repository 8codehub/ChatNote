package com.chatnote.directnotesui.processing

import android.content.Context
import android.net.Uri
import com.chatnote.directnotesui.model.UiNoteExtra
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class ExtraProcessorImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : ExtraProcessor {

    override suspend fun copyToLocalStorage(
        items: List<UiNoteExtra>
    ): List<UiNoteExtra> = coroutineScope {
        items.map { extra ->
            async { copyExtra(extra) }
        }.awaitAll().filterNotNull()
    }

    private suspend fun copyExtra(extra: UiNoteExtra): UiNoteExtra? = when (extra) {
        is UiNoteExtra.UiNoteImageExtra -> copyImageExtra(extra)
    }

    private suspend fun copyImageExtra(
        extra: UiNoteExtra.UiNoteImageExtra
    ): UiNoteExtra.UiNoteImageExtra? = withContext(Dispatchers.IO) {
        try {
            val sourceUri = Uri.parse(extra.uri)
            val inputStream =
                context.contentResolver.openInputStream(sourceUri) ?: return@withContext null

            val extension = getFileExtensionFromUri(sourceUri) ?: "jpg"
            val uniqueSuffix = System.nanoTime().toString() + "_" + (0..99999).random()
            val fileName = "image_${uniqueSuffix}.$extension"
            val file = File(context.filesDir, fileName)

            FileOutputStream(file).use { outputStream ->
                inputStream.use { it.copyTo(outputStream) }
            }

            UiNoteExtra.UiNoteImageExtra(uri = Uri.fromFile(file).toString())
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun getFileExtensionFromUri(uri: Uri): String? {
        val name = uri.lastPathSegment ?: return null
        val dotIndex = name.lastIndexOf('.')
        return if (dotIndex != -1 && dotIndex < name.length - 1) {
            name.substring(dotIndex + 1)
        } else null
    }
}