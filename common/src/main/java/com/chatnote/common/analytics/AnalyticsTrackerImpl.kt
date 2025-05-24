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

    override fun trackFolderCount(folderCount: Int) {
        logEvent("folder_count_updated", Bundle().apply {
            putInt("folder_count", folderCount)
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
        logEvent("folder_opened", Bundle().apply {
            putLong("folder_id", folderId)
            putInt("notes_count", notesCount)
        })
    }

    override fun trackFolderPinned(folderId: Long, isPinned: Boolean) {
        logEvent("folder_pinned", Bundle().apply {
            putLong("folder_id", folderId)
            putBoolean("pinned", isPinned)
        })
    }

    override fun trackFolderDeleted(folderId: Long, messagesCount: Int) {
        logEvent("folder_deleted", Bundle().apply {
            putLong("folder_id", folderId)
            putInt("messages_count", messagesCount)
        })
    }

    override fun trackFolderEditDone(iconUri: String, isEditMode: Boolean) {
        logEvent("folder_edit_done", Bundle().apply {
            putString("icon_uri", iconUri)
            putBoolean("is_editor_mode", isEditMode)
        })
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

    override fun trackOnReviewRequestFail(message: String?) {
        logEvent("review_request_fail_".plus(message?.take(15)))
    }

    override fun trackOnReviewRequestSuccess() {
        logEvent("review_request_success")
    }

    override fun trackReviewRequest() {
        logEvent("review_request")
    }

    override fun trackOnReviewLaunchFail(message: String?) {
        logEvent("on_review_launch_fail_".plus(message?.take(15)))
    }

    override fun trackOnReviewLaunchSuccess() {
        logEvent("on_review_launch_success")
    }

    private fun logEvent(eventName: String, params: Bundle? = null) {
        firebaseAnalytics.logEvent(eventName, params)
    }
}
