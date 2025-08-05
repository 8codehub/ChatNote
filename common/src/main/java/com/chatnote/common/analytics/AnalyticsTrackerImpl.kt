package com.chatnote.common.analytics

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class AnalyticsTrackerImpl @Inject constructor(
    private val firebaseAnalytics: FirebaseAnalytics
) : AnalyticsTracker {

    override fun trackNewNote(folderId: Long) {
        logEvent("new_note_added", Bundle().apply {
            putLong("folder_id", folderId)
        })
    }

    override fun trackAppStart(firstStart: Boolean) {
        logEvent("app_start", Bundle().apply {
            putBoolean("first_start", firstStart)
        })
    }

    override fun trackFolderEditOpened(isEditMode: Boolean) {
        logEvent("folder_edit_opened", Bundle().apply {
            putBoolean("is_edit_mode", isEditMode)
        })
    }

    override fun trackGeneralError(message: String, src: String) {
        logEvent("general_error", Bundle().apply {
            putString("message", message)
            putString("source", src)
        })
    }

    override fun trackFolderOpen(folderId: Long, notesCount: Int) {
        logEvent("folder_opened_notes_count_${notesCount}")
    }

    override fun trackFolderPinned(folderId: Long, isPinned: Boolean) {
        logEvent("folder_pinned", Bundle().apply {
            putLong("folder_id", folderId)
            putBoolean("pinned", isPinned)
        })
    }

    override fun trackFolderDeleted(folderId: Long, messagesCount: Int) {
        logEvent("folder_deleted_notes_count_${messagesCount}")
    }

    override fun trackFolderEditDone(iconUri: String, isEditMode: Boolean) {
        logEvent("folder_edit_done_icon_${iconUri}_edit_mode_$isEditMode")
    }

    override fun onNotificationReceived(title: String, body: String) {
        logEvent("on_notification_received", Bundle().apply {
            putString("title", title)
            putString("body", body)
        })
    }

    override fun trackNoteDetailActionDone(interaction: String) {
        logEvent(interaction)
    }

    override fun trackNoteLongClick() {
        logEvent("note_long_click")
    }

    override fun trackCameraClick() {
        logEvent("camera_click")
    }

    override fun trackCameraImageTaken() {
        logEvent("camera_image_taken")
    }

    override fun trackGalleryImageTaken() {
        logEvent("gallery_image_taken")
    }

    override fun trackImageRemove() {
        logEvent("image_remove")
    }

    override fun trackImagesAttached() {
        logEvent("images_attached")
    }

    override fun trackImageViewerOpen() {
        logEvent("image_viewer_open")
    }

    override fun trackNavigationRoute(navigationRoute: String) {
        logEvent("rout_$navigationRoute")
    }


    private fun logEvent(eventName: String, params: Bundle? = null) {
        firebaseAnalytics.logEvent(eventName, params)
    }
}
