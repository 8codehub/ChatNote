package com.chatnote.coreui.ui.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun SwappableItem(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
    onStateChange: (SwappableItemState) -> Unit,
    showOnboarding: Boolean = false,
    onOnboardingFinished: () -> Unit = {},
    actionButtonsContent: @Composable RowScope.() -> Unit,
    swappableItemState: SwappableItemState = SwappableItemState.Default
) {
    var actionWidth by remember { mutableFloatStateOf(0f) }
    val swipeOffset = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(showOnboarding) {
        if (showOnboarding) {
            kotlinx.coroutines.delay(1000L)
            swipeOffset.animateTo(-actionWidth, tween(300))
            kotlinx.coroutines.delay(600L)
            swipeOffset.animateTo(0f, tween(300))
            onOnboardingFinished()
        }
    }

    LaunchedEffect(swappableItemState) {
        when (swappableItemState) {
            SwappableItemState.Open -> {
                swipeOffset.animateTo(-actionWidth, tween(300))
                onStateChange(SwappableItemState.Default)
            }

            SwappableItemState.Close -> {
                swipeOffset.animateTo(0f, tween(300))
                onStateChange(SwappableItemState.Default)
            }

            SwappableItemState.Default -> {
                // Do nothing
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onDragEnd = {
                        scope.launch {
                            if (swipeOffset.value < -actionWidth / 2) {
                                swipeOffset.animateTo(-actionWidth, tween(300))
                            } else {
                                swipeOffset.animateTo(0f, tween(300))
                            }
                        }
                    },
                    onDragCancel = {
                        scope.launch {
                            swipeOffset.animateTo(0f, tween(300))
                        }
                    },
                    onHorizontalDrag = { _, dragAmount ->
                        scope.launch {
                            val target = swipeOffset.value + dragAmount
                            swipeOffset.snapTo(target.coerceIn(-actionWidth, 0f))
                        }
                    }
                )
            }
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
        ) {
            Layout(
                content = {
                    Row(
                        modifier = Modifier,
                        content = actionButtonsContent
                    )
                },
                measurePolicy = { measurables, constraints ->
                    val limitedConstraints =
                        constraints.copy(maxHeight = constraints.maxHeight.coerceAtMost(5000))
                    val placeables = measurables.map { it.measure(limitedConstraints) }
                    val width = placeables.sumOf { it.width }
                    val height = placeables.maxOfOrNull { it.height } ?: 0
                    actionWidth = width.toFloat()
                    layout(width, height) {
                        var xPosition = 0
                        placeables.forEach { placeable ->
                            placeable.placeRelative(xPosition, 0)
                            xPosition += placeable.width
                        }
                    }
                }
            )
        }
        Box(
            modifier = Modifier
                .offset(x = (swipeOffset.value / 2).dp)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface),
            contentAlignment = Alignment.CenterStart
        ) {
            content()
        }
    }
}

enum class SwappableItemState {
    Open, Close, Default
}
