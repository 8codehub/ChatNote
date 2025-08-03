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
    fun trackReviewRequest()
    fun trackNewNote(folderId: Long)
    fun trackOnReviewLaunchSuccess()
    fun trackOnReviewRequestSuccess()
    fun trackAppStart(firstStart: Boolean)
    fun trackFolderCount(folderCount: Int)
    fun trackOnReviewLaunchFail(message: String?)
    fun trackFolderEditOpened(isEditMode: Boolean)
    fun trackOnReviewRequestFail(message: String?)
    fun trackNoteDetailActionDone(interaction: String)
    fun trackGeneralError(message: String, src: String)
    fun trackFolderOpen(folderId: Long, notesCount: Int)
    fun trackFolderPinned(folderId: Long, isPinned: Boolean)
    fun trackFolderDeleted(folderId: Long, messagesCount: Int)
    fun trackFolderEditDone(iconUri: String, isEditMode: Boolean)
    fun onNotificationReceived(title: String, body: String)
}
