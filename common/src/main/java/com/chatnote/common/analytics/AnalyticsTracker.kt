package com.chatnote.common.analytics

interface AnalyticsTracker {

    fun trackNoteLongClick()
    fun trackCameraClick()
    fun trackCameraImageTaken()
    fun trackGalleryImageTaken()
    fun trackImageRemove()
    fun trackImagesAttached()
    fun trackImageViewerOpen()
    fun trackNavigationRoute(navigationRoute: String)
    fun trackNewNote(folderId: Long)
    fun trackAppStart(firstStart: Boolean)
    fun trackFolderEditOpened(isEditMode: Boolean)
    fun trackNoteDetailActionDone(interaction: String)
    fun trackGeneralError(message: String, src: String)
    fun trackFolderOpen(folderId: Long, notesCount: Int)
    fun trackFolderPinned(folderId: Long, isPinned: Boolean)
    fun trackFolderDeleted(folderId: Long, messagesCount: Int)
    fun trackFolderEditDone(iconUri: String, isEditMode: Boolean)
    fun onNotificationReceived(title: String, body: String)
}
