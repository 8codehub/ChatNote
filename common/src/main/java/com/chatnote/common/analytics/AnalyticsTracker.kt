package com.chatnote.common.analytics

interface AnalyticsTracker {
    fun trackNewNote(folderId: Long)
    fun trackAppStart(firstStart: Boolean)
    fun trackFolderCount(folderCount: Int)
    fun trackFolderEditOpened(isEditMode: Boolean)
    fun trackGeneralError(message: String, src: String)
    fun trackFolderOpen(folderId: Long, notesCount: Int)
    fun trackFolderPinned(folderId: Long, isPinned: Boolean)
    fun trackFolderDeleted(folderId: Long, messagesCount: Int)
    fun trackFolderEditDone(iconUri: String, isEditMode: Boolean)
}
