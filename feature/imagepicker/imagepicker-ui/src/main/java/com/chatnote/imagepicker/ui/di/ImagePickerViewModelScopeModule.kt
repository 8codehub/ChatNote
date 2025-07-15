package com.chatnote.imagepicker.ui.di

import com.chatnote.coreui.arch.StatefulEventHandler
import com.chatnote.imagepicker.ui.ImagePickerContract
import com.chatnote.imagepicker.ui.ImagePickerStatefulEventHandler
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class ImagePickerViewModelScopeModule {

    @Binds
    abstract fun bindDirectNotesStatefulEventHandler(
        implementation: ImagePickerStatefulEventHandler
    ): StatefulEventHandler<
            ImagePickerContract.ImagePickerEvent,
            ImagePickerContract.ImagePickerOneTimeEvent,
            ImagePickerContract.ImagePickerState,
            ImagePickerContract.MutableImagePickerState
            >

}
