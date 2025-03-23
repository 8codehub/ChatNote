package com.chatnote.coredomain.usecase

import com.google.android.play.core.review.ReviewInfo

interface ReviewUseCase {
    suspend operator fun invoke(): Result<ReviewInfo>
}