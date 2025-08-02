package com.chatnote.coreui.ui.component

import android.widget.Toast
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.changedToUpIgnoreConsumed
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import kotlinx.coroutines.launch

@Composable
fun ZoomableImage(
    imageUrl: String,
    modifier: Modifier = Modifier,
    onZoomingChanged: (Boolean) -> Unit = {}
) {
    val context = LocalContext.current

    val scale = remember { Animatable(1f) }
    val offsetX = remember { Animatable(0f) }
    val offsetY = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()

    var activePointers by remember { mutableStateOf(0) }
    var hasShownZoomToast by remember { mutableStateOf(false) }
    var shouldShowReleaseToast by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .pointerInput(Unit) {
                detectTransformGestures(panZoomLock = true) { _, pan, zoom, _ ->
                    if (activePointers >= 2) {
                        if (!hasShownZoomToast) {
                            Toast.makeText(context, "Zoom started", Toast.LENGTH_SHORT).show()
                            hasShownZoomToast = true
                        }
                        shouldShowReleaseToast = true
                        coroutineScope.launch {
                            val newScale = (scale.value * zoom).coerceIn(1f, 3f)
                            scale.snapTo(newScale)
                            offsetX.snapTo(offsetX.value + pan.x)
                            offsetY.snapTo(offsetY.value + pan.y)
                        }
                    } else {

                    }
                }
            }
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        activePointers = event.changes.count { it.pressed }

                        onZoomingChanged(scale.value > 1f || activePointers >= 2)

                        if (event.changes.all { it.changedToUpIgnoreConsumed() }) {
                            activePointers = 0
                            if (scale.value > 1f) {
                                coroutineScope.launch {
                                    scale.animateTo(1f, tween(300))
                                    offsetX.animateTo(0f, tween(300))
                                    offsetY.animateTo(0f, tween(300))

                                    // ✅ Only show toast once animation is complete
                                    if (shouldShowReleaseToast) {
                                        Toast.makeText(context, "Zoom released", Toast.LENGTH_SHORT)
                                            .show()
                                        shouldShowReleaseToast = false
                                        hasShownZoomToast = false
                                    }
                                }
                            } else {
                                hasShownZoomToast = false
                                shouldShowReleaseToast = false
                            }

                            onZoomingChanged(false)
                        }
                    }
                }
            }
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    scaleX = scale.value
                    scaleY = scale.value
                    translationX = offsetX.value
                    translationY = offsetY.value
                }

        )
    }
}
