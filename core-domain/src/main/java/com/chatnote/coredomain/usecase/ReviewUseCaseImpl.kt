package com.chatnote.coredomain.usecase

import com.google.android.play.core.review.ReviewException
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManager
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class ReviewUseCaseImpl @Inject constructor(
    private val reviewManager: ReviewManager
) : ReviewUseCase {

    override suspend operator fun invoke(): Result<ReviewInfo> =
        suspendCancellableCoroutine { cont ->
            val request = reviewManager.requestReviewFlow()
            request.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    cont.resume(Result.success(task.result))
                } else {
                    val exception = task.exception as? ReviewException
                    cont.resume(Result.failure(exception ?: Exception("Unknown error")))
                }
            }
        }
}