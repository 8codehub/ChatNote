package com.sendme.ui.folderlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sendme.domain.model.Folder
import com.pingpad.coreui.component.ui.component.StyledText
import com.sendme.coreui.component.ui.component.CircularImage
import com.sendme.homelistui.R

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.launch

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale

@Composable
fun ImprovedSwipableFolderItem(
    folderName: String,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onPin: () -> Unit
) {
    val actionWidth = 180f // Width of the actions (60dp * 3 buttons)
    val swipeOffset = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
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
                    onHorizontalDrag = { change, dragAmount ->
                        scope.launch {
                            val target = swipeOffset.value + dragAmount
                            // Restrict swipe between 0 and -actionWidth
                            swipeOffset.snapTo(target.coerceIn(-actionWidth, 0f))
                        }
                    }
                )
            }
    ) {
        // Background with action buttons
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.secondary),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ActionIcon(
                title = "Edit",
                color = Color.Blue,
                onClick = onEdit
            )
            ActionIcon(
                title = "Delete",
                color = Color.Red,
                onClick = onDelete
            )
            ActionIcon(
                title = "Pin",
                color = Color.Green,
                onClick = onPin
            )
        }

        // Foreground content
        Box(
            modifier = Modifier
                .offset(x = swipeOffset.value.dp)
                .fillMaxWidth()
                .height(60.dp)
                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp))
                .padding(16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = folderName,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun ActionIcon(title: String, color: Color, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .width(60.dp)
            .fillMaxHeight()
            .background(color)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            color = Color.White,
            style = MaterialTheme.typography.labelSmall
        )
    }
}


@Composable
fun FolderCard(
    folder: Folder,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
            .padding(horizontal = 16.dp)
    ) {

        CircularImage(
            modifier = Modifier.padding(vertical = 4.dp),
            imageUrl = folder.iconUri.orEmpty(),
            iconSize = 56.dp,
            //   borderColor = MaterialTheme.colorScheme.primary,
            iconPadding = 0.dp,
            // imageColorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary),
            //   backgroundColor = MaterialTheme.colorScheme.t,
            borderWidth = 0.dp
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            StyledText(
                text = folder.name,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onBackground
            )

            StyledText(
                text = folder.lastNote.ifEmpty {
                    stringResource(R.string.empty_folder_hint)
                },
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                maxLines = 2,
                fontStyle = if (folder.lastNote.isEmpty()) {
                    FontStyle.Italic
                } else {
                    FontStyle.Normal
                },
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.secondary
            )
        }

        if (folder.isPinned) {
            CircularImage(
                modifier = Modifier.rotate(45f),
                iconSize = 18.dp,
                drawableRes = R.drawable.ic_pin,
                iconPadding = 6.dp,
                contentScale = ContentScale.Fit,
                imageColorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                backgroundColor = Color.Transparent
            )
        }

    }

}