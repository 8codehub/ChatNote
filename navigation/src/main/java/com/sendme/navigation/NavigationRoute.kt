package com.sendme.navigation

import kotlinx.serialization.Serializable

sealed class NavigationRoute {

    @Serializable
    data object HomeList : NavigationRoute()

    @Serializable
    data class FolderEditor(
        val folderId: Long? = null
    ) : NavigationRoute()

    @Serializable
    data class DirectNotes(
        val folderId: Long
    ) : NavigationRoute()
}