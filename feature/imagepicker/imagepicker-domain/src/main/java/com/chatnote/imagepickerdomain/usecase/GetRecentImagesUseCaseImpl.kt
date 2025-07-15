package com.chatnote.imagepickerdomain.usecase


import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import com.chatnote.imagepickerdomain.usecase.GetRecentImagesUseCase
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

internal class GetRecentImagesUseCaseImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : GetRecentImagesUseCase {

    override suspend fun invoke(): List<Uri> {
        val imageUris = mutableListOf<Uri>()
        val projection = arrayOf(MediaStore.Images.Media._ID)
        val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

        try {
            context.contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                sortOrder
            )?.use { cursor ->
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                var count = 0
                while (cursor.moveToNext() && count < 10) {
                    val id = cursor.getLong(idColumn)
                    val uri = Uri.withAppendedPath(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        id.toString()
                    )
                    imageUris.add(uri)
                    count++
                }
            }

        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        return imageUris
    }
}