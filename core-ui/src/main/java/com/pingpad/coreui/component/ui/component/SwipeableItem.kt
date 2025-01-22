package com.pingpad.coreui.component.ui.component

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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SwipeableItem(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
    onStateChange: (SwipeableItemState) -> Unit,
    actionButtonsContent: @Composable RowScope.() -> Unit,
    swipeableItemState: SwipeableItemState = SwipeableItemState.Default
) {
    var actionWidth by remember { mutableFloatStateOf(0f) }
    val swipeOffset = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(swipeableItemState) {
        when (swipeableItemState) {
            SwipeableItemState.Open -> {
                swipeOffset.animateTo(-actionWidth, tween(300)) // Open with animation
                onStateChange(SwipeableItemState.Default) // Reset state to Default
            }
            SwipeableItemState.Close -> {
                swipeOffset.animateTo(0f, tween(300)) // Close with animation
                onStateChange(SwipeableItemState.Default) // Reset state to Default
            }
            SwipeableItemState.Default -> {
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
                                // Snap to fully reveal actions
                                swipeOffset.animateTo(-actionWidth, tween(300))
                            } else {
                                // Snap back to idle position
                                swipeOffset.animateTo(0f, tween(300))
                            }
                        }
                    },
                    onDragCancel = {
                        scope.launch {
                            // Reset to idle position
                            swipeOffset.animateTo(0f, tween(300))
                        }
                    },
                    onHorizontalDrag = { _, dragAmount ->
                        scope.launch {
                            val target = swipeOffset.value + dragAmount
                            // Restrict swipe between 0 and -actionWidth
                            swipeOffset.snapTo(target.coerceIn(-actionWidth, 0f))
                        }
                    }
                )
            }
    ) {
        // Action buttons container
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
                    // Ensure constraints are valid and have finite dimensions
                    val limitedConstraints = constraints.copy(maxHeight = constraints.maxHeight.coerceAtMost(5000)) // Limit max height
                    val placeables = measurables.map { it.measure(limitedConstraints) }
                    val width = placeables.sumOf { it.width }
                    val height = placeables.maxOfOrNull { it.height } ?: 0
                    actionWidth = width.toFloat() // Dynamically set the total action width
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

        // Foreground content
        Box(
            modifier = Modifier
                .offset(x = (swipeOffset.value/2).dp)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface),
            contentAlignment = Alignment.CenterStart
        ) {
            content()
        }
    }
}

enum class SwipeableItemState {
    Open, Close, Default
}
