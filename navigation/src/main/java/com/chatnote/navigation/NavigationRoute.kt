package com.chatnote.navigation

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

    @Serializable
    data class EditNote(
        val noteId: Long
    ) : NavigationRoute()
}