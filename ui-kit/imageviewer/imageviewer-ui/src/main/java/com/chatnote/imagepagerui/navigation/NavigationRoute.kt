package com.chatnote.imagepagerui.navigation

import kotlinx.serialization.Serializable

internal sealed class PagerNavigationRoute {

    @Serializable
    data class ImagesList(val images: List<String>) :
        PagerNavigationRoute()

    @Serializable
    data class ImagePreview(
        val selectedImage: String
    ) : PagerNavigationRoute()
}