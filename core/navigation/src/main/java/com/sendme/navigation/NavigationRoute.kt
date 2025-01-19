package com.sendme.navigation

@Serializable
data object HomeListScreen

@Serializable
data class FolderEditorScreen(val folderId: Long? = null)

@Serializable
data class DirectNotesScreen(val folderId: Long)


sealed class NavigationRoute() {
    object Home : NavigationRoute("home")
    object Settings : NavigationRoute("settings")
    object Profile : NavigationRoute("profile/{userId}") {
        fun createRoute(userId: String): String = "profile/$userId"
    }
}