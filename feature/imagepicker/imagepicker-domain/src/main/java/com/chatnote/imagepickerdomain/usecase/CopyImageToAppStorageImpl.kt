package com.chatnote.imagepickerdomain.usecase

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class CopyImageToAppStorageImpl @Inject constructor() : CopyImageToAppStorage {
    override suspend fun invoke(context: Context, sourceUri: Uri): Uri? {
        return try {
            val inputStream = context.contentResolver.openInputStream(sourceUri) ?: return null
            val fileName = "image_${System.currentTimeMillis()}.jpg"
            val file = File(context.filesDir, fileName)
            val outputStream = FileOutputStream(file)

            inputStream.copyTo(outputStream)

            inputStream.close()
            outputStream.close()

            Uri.fromFile(file)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}