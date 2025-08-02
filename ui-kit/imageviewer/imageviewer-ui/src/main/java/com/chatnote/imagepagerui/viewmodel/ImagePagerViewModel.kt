package com.chatnote.imagepagerui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.chatnote.common.di.IoDispatcher
import com.chatnote.coreui.arch.EventDrivenViewModel
import com.chatnote.coreui.arch.StatefulEventHandler
import com.chatnote.imagepagerui.ImagePagerContract
import com.chatnote.navigation.NavigationRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject


@HiltViewModel
class ImagePagerViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    @IoDispatcher ioDispatcher: CoroutineDispatcher,
    statefulEventHandler: StatefulEventHandler<
            ImagePagerContract.ImagePagerEvent,
            ImagePagerContract.ImagePagerOneTimeEvent,
            ImagePagerContract.ImagePagerState,
            ImagePagerContract.MutableImagePagerState
            >
) : EventDrivenViewModel<
        ImagePagerContract.ImagePagerState,
        ImagePagerContract.MutableImagePagerState,
        ImagePagerContract.ImagePagerEvent,
        ImagePagerContract.ImagePagerOneTimeEvent,
        StatefulEventHandler<
                ImagePagerContract.ImagePagerEvent,
                ImagePagerContract.ImagePagerOneTimeEvent,
                ImagePagerContract.ImagePagerState,
                ImagePagerContract.MutableImagePagerState
                >
        >(statefulEventHandler = statefulEventHandler, ioDispatcher = ioDispatcher) {

    private val args = savedStateHandle.toRoute<NavigationRoute.ImagePager>()

    override fun onStateReady() {
        ImagePagerContract.ImagePagerEvent.LoadImages(
            defaultImage = args.selectedImage,
            images = args.images
        ).processWithLaunch()
    }

    override fun onGeneralError(throwable: Throwable) {
        ImagePagerContract.ImagePagerEvent.GeneralError(throwable = throwable).processWithLaunch()
    }
}