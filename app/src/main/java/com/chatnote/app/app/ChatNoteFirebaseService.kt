package com.chatnote.app.app

import com.chatnote.common.analytics.AnalyticsTracker
import com.chatnote.coreui.util.NotificationHandler
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ChatNoteFirebaseService @Inject constructor() : FirebaseMessagingService() {

    @Inject
    lateinit var notificationHandler: NotificationHandler

    @Inject
    lateinit var analyticsTracker: AnalyticsTracker

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        val title = remoteMessage.notification?.title ?: "New message"
        val body = remoteMessage.notification?.body ?: "You have a new notification"
        analyticsTracker.onNotificationReceived(title = title, body = body)
        notificationHandler.show(title, body)
    }
}
