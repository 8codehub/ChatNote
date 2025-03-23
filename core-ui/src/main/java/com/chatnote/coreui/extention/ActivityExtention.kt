package com.chatnote.coreui.extention

import android.app.Activity
import com.chatnote.common.analytics.AnalyticsTracker
import com.google.android.play.core.review.ReviewManager

fun Activity.launchInAppReview(
    reviewManagerProvider: () -> ReviewManager,
    analyticsTrackerProvider: () -> AnalyticsTracker
) {
    val reviewManager = reviewManagerProvider()
    val analytics = analyticsTrackerProvider()

    val request = reviewManager.requestReviewFlow()
    analytics.trackReviewRequest()

    request.addOnCompleteListener { task ->
        if (task.isSuccessful) {
            analytics.trackOnReviewRequestSuccess()
            val reviewInfo = task.result
            reviewManager.launchReviewFlow(this, reviewInfo)
                .addOnSuccessListener {
                    analytics.trackOnReviewLaunchSuccess()
                }
                .addOnFailureListener {
                    analytics.trackOnReviewLaunchFail(message = it.message)
                }
        } else {
            analytics.trackOnReviewRequestFail(message = task.exception?.message.orEmpty())
        }
    }
}
