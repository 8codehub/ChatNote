package com.chatnote.common.analytics

interface AnalyticsTracker {
    fun trackFolderDeleted(folderId: Long, messagesCount: Int)
    fun trackFolderPinned(folderId: Long, isPinned: Boolean)
    fun trackFolderCount(folderCount: Int)

    fun trackFolderEditOpened(isEditMode: Boolean)
    fun trackFolderEditDone(iconUri: String, isEditMode: Boolean)

    fun trackNewNote(folderId: Long)
    fun trackAppStart(firstStart: Boolean)
    fun trackGeneralError(message: String, src: String)
    fun trackFolderOpen(folderId: Long, notesCount: Int)
}
