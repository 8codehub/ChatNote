package com.chatnote.imagepicker.ui.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chatnote.coreui.ui.component.StyledText
import com.chatnote.coreui.util.PermissionType
import com.chatnote.coreui.util.permissionRequestLauncher
import com.chatnote.imagepicker.ui.ImagePickerContract
import com.chatnote.imagepicker.ui.components.CameraButton
import com.chatnote.imagepicker.ui.components.GalleryButton
import com.chatnote.imagepicker.ui.components.RoundedAsyncImage
import com.chatnote.imagepicker.ui.model.AttachMode
import com.chatnote.imagepicker.ui.viewmodel.BaseImagePickerViewModel
import com.chatnote.imagepicker.ui.viewmodel.MultiAttachImagePickerViewModel
import com.chatnote.imagepicker.ui.viewmodel.SingleAttachImagePickerViewModel
import kotlinx.coroutines.flow.collectLatest
import com.chatnote.content.R as CR


@Composable
fun AttachSingleImageBottomSheet(
    viewModel: SingleAttachImagePickerViewModel = hiltViewModel<SingleAttachImagePickerViewModel>(),
    onDismiss: () -> Unit,
    onImagePicked: (Uri) -> Unit,
) {
    AttachImageBottomSheet(
        attachMode = AttachMode.SingleAttach,
        viewModel = viewModel,
        onDismiss = onDismiss,
        onImagesPicked = {
            it.firstOrNull()?.let { it1 -> onImagePicked(it1) }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttachMultiImageBottomSheet(
    viewModel: MultiAttachImagePickerViewModel = hiltViewModel<MultiAttachImagePickerViewModel>(),
    onDismiss: () -> Unit,
    onImagesPicked: (List<Uri>) -> Unit,
) {

    AttachImageBottomSheet(
        viewModel = viewModel,
        onDismiss = onDismiss,
        onImagesPicked = onImagesPicked,
        attachMode = AttachMode.MultiAttach
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AttachImageBottomSheet(
    attachMode: AttachMode,
    viewModel: BaseImagePickerViewModel,
    onDismiss: () -> Unit,
    onImagesPicked: (List<Uri>) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val pendingCameraUri = remember { mutableStateOf<Uri?>(null) }

    val requestCameraPermission = permissionRequestLauncher(
        type = PermissionType.CAMERA,
        onGranted = viewModel::openCamera,
        onDenied = { println("Permission_tag ImagePicker ❌ Camera permission denied") }
    )

    val requestGalleryPermission = permissionRequestLauncher(
        type = PermissionType.GALLERY,
        onGranted = viewModel::loadImages,
        onDenied = { println("Permission_tag ImagePicker ❌ Gallery permission denied") }
    )

    val openGalleryWithPermission = permissionRequestLauncher(
        type = PermissionType.GALLERY,
        onGranted = {
            viewModel.loadImages()
            viewModel.openGallery()
        },
        onDenied = { println("Permission_tag ImagePicker ❌ Gallery permission denied") }
    )

    val enableAttachButton by remember(uiState.allImages) { derivedStateOf { uiState.allImages.any { it.isSelected } } }

    LaunchedEffect(Unit) {
        requestGalleryPermission.launch(PermissionType.GALLERY.toSystemPermission())
    }

    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            pendingCameraUri.value?.let(viewModel::onCameraImageTaken)
        }
    }

    val pickMultipleImagesLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris ->
        if (uris.isNotEmpty()) viewModel.addGalleryImages(uris)
    }

    val pickSingleImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { viewModel.addGalleryImages(listOf(it)) }
    }

    LaunchedEffect(Unit) {
        viewModel.oneTimeEvent.collectLatest { event ->
            when (event) {
                is ImagePickerContract.ImagePickerOneTimeEvent.OpenCamera -> {
                    pendingCameraUri.value = event.uri
                    takePictureLauncher.launch(event.uri)
                }

                is ImagePickerContract.ImagePickerOneTimeEvent.ImagesSelected -> {
                    onImagesPicked(event.uris)
                    onDismiss()
                }

                is ImagePickerContract.ImagePickerOneTimeEvent.OpenGallery -> {
                    when (attachMode) {
                        AttachMode.SingleAttach -> pickSingleImageLauncher.launch("image/*")
                        AttachMode.MultiAttach -> pickMultipleImagesLauncher.launch("image/*")
                    }

                }
            }
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.secondaryContainer
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 80.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 300.dp), // adjust height as needed
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(8.dp)
            ) {
                item {
                    CameraButton(modifier = Modifier.size(80.dp)) {
                        requestCameraPermission.launch(PermissionType.CAMERA.toSystemPermission())
                    }
                }

                items(
                    uiState.allImages.size,
                    key = { uiState.allImages[it].uri.toString() }) { index ->
                    val item = uiState.allImages[index]
                    RoundedAsyncImage(
                        model = item.uri,
                        contentDescription = null,
                        selected = item.isSelected,
                        onClick = { viewModel.toggleImageSelection(item.uri) }
                    )
                }

                item {
                    GalleryButton(modifier = Modifier.size(80.dp)) {
                        openGalleryWithPermission.launch(PermissionType.GALLERY.toSystemPermission())
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                enabled = enableAttachButton,
                onClick = {
                    onImagesPicked(uiState.allImages.filter { it.isSelected }.map { it.uri })
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(
                    disabledContainerColor = MaterialTheme.colorScheme.primary.copy(
                        alpha = 0.5f
                    ),
                ),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                StyledText(
                    text = stringResource(CR.string.attach),
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.W500,
                    fontSize = 14.sp,
                    lineHeight = 14.sp
                )
            }
        }
    }
}