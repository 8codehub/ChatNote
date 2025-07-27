package com.chatnote.imagepickerdomain.usecase

import android.net.Uri

fun interface GenerateCameraUriUseCase {
    operator fun invoke(): Uri
}

fun interface GetRecentImagesUseCase {
    suspend operator fun invoke(): List<Uri>
}
