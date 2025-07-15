package com.chatnote.imagepickerdomain.usecase

import android.content.Context
import android.net.Uri

fun interface GenerateCameraUriUseCase {
    operator fun invoke(): Uri
}

fun interface GetRecentImagesUseCase {
    suspend operator fun invoke(): List<Uri>
}

fun interface CopyImageToAppStorage {
    suspend operator fun invoke(context: Context, sourceUri: Uri): Uri?
}