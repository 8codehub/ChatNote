package com.chatnote.imagepagerui

import com.chatnote.common.analytics.AnalyticsTracker
import com.chatnote.coredomain.mapper.Mapper
import com.chatnote.coreui.arch.StatefulEventHandler
import com.chatnote.imagepagerui.ImagePagerContract.ImagePagerEvent
import com.chatnote.imagepagerui.ImagePagerContract.ImagePagerOneTimeEvent
import com.chatnote.imagepagerui.ImagePagerContract.ImagePagerOneTimeEvent.FailedOperation
import com.chatnote.imagepagerui.ImagePagerContract.ImagePagerState
import com.chatnote.imagepagerui.ImagePagerContract.MutableImagePagerState
import javax.inject.Inject

internal class ImagePagerStatefulEventHandler @Inject constructor(
    private val analyticsTracker: AnalyticsTracker,
    private val mapperResultErrorToErrorId: Mapper<Throwable?, Int>
) :
    StatefulEventHandler<ImagePagerEvent, ImagePagerOneTimeEvent, ImagePagerState, MutableImagePagerState>(
        ImagePagerState()
    ) {
    override suspend fun process(event: ImagePagerEvent, args: Any?) {
        when (event) {
            is ImagePagerEvent.GeneralError -> onGeneralError(event.throwable)
            is ImagePagerEvent.LoadImages -> onLoadImagesEvent(event.images, event.defaultImage)
            is ImagePagerEvent.NavigationEvent -> analyticsTracker.trackNavigationRoute(
                navigationRoute = event.navigationRoute.toString().lowercase()
            )
        }
    }

    private fun onLoadImagesEvent(imagePaths: List<String>, preSelectedImage: String?) {
        analyticsTracker.trackImageViewerOpen()
        val sortedImages = imagePaths.sortedBy { image ->
            if (image == preSelectedImage) 0 else 1
        }
        updateUiState {
            images = sortedImages
        }
    }

    private suspend fun onGeneralError(throwable: Throwable) {
        analyticsTracker.trackGeneralError(
            message = throwable.message.orEmpty(),
            src = this.javaClass.name
        )
        FailedOperation(error = mapperResultErrorToErrorId.map(throwable))
            .processOneTimeEvent()
    }
}