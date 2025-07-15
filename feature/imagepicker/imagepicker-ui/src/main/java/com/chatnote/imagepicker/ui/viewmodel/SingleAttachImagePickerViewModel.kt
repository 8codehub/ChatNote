package com.chatnote.imagepicker.ui.viewmodel

import com.chatnote.common.di.IoDispatcher
import com.chatnote.coreui.arch.StatefulEventHandler
import com.chatnote.imagepicker.ui.ImagePickerContract
import com.chatnote.imagepicker.ui.model.AttachMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@HiltViewModel
class SingleAttachImagePickerViewModel @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    statefulEventHandler: StatefulEventHandler<
            ImagePickerContract.ImagePickerEvent,
            ImagePickerContract.ImagePickerOneTimeEvent,
            ImagePickerContract.ImagePickerState,
            ImagePickerContract.MutableImagePickerState
            >
) : BaseImagePickerViewModel(dispatcher = dispatcher, statefulEventHandler = statefulEventHandler) {
    override val attachMode: AttachMode = AttachMode.SingleAttach
}