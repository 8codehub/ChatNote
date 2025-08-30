package com.chatnote.imagepagerui.di

import com.chatnote.coreui.arch.StatefulEventHandler
import com.chatnote.imagepagerui.ImagePagerContract
import com.chatnote.imagepagerui.ImagePagerStatefulEventHandler
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class ImagePagerViewModelScopeModule {

    @Binds
    abstract fun bindImagePagerStatefulEventHandler(
        implementation: ImagePagerStatefulEventHandler
    ): StatefulEventHandler<
            ImagePagerContract.ImagePagerEvent,
            ImagePagerContract.ImagePagerOneTimeEvent,
            ImagePagerContract.ImagePagerState,
            ImagePagerContract.MutableImagePagerState
            >

}