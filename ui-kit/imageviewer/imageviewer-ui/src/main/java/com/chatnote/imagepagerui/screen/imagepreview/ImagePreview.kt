package com.chatnote.imagepagerui.screen.imagepreview

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chatnote.coreui.ui.component.ZoomableImage
import com.chatnote.imagepagerui.components.ImagePagerTopAppBar

@Composable
fun ImagePreviewScreen(
    imageUrl: String,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            ImagePagerTopAppBar(
                onBackClick = onBackClick
            )
        }
    ) { padding ->
        ZoomableImage(
            imageUrl = imageUrl,
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        )
    }
}