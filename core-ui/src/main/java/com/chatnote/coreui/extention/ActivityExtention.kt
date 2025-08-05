package com.chatnote.coreui.extention

import android.app.Activity
import com.chatnote.common.analytics.AnalyticsTracker
import com.google.android.play.core.review.ReviewManager

fun Activity.launchInAppReview(
    reviewManagerProvider: () -> ReviewManager,
) {
    val reviewManager = reviewManagerProvider()
    val request = reviewManager.requestReviewFlow()

    request.addOnCompleteListener { task ->
        if (task.isSuccessful) {
            val reviewInfo = task.result
            reviewManager.launchReviewFlow(this, reviewInfo)
        }
    }
}
