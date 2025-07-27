package com.chatnote.directnotesui.directnoteslist.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chatnote.coreui.ui.component.StyledText
import com.chatnote.directnotesui.model.UiNote

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteItem(
    note: UiNote,
    onLongClick: ((UiNote) -> Unit) = { }
) {
    val bubbleWidth = getBubbleWidth()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .widthIn(max = bubbleWidth)
                .padding(horizontal = 4.dp)
                .combinedClickable(
                    onClick = { },
                    onLongClick = { onLongClick(note) }
                )
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = MaterialTheme.shapes.large
                )
                .padding(vertical = 8.dp, horizontal = 12.dp),
        ) {
            StyledText(
                text = note.content,
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 18.sp,

                )

            StyledText(
                text = note.date,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                maxLines = 1,
                fontStyle = FontStyle.Italic,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSecondary
            )

            if (note.imagePaths.isNotEmpty()) {

                ExtraImagesGrid(
                    imageUrls = note.imagePaths,
                    modifier = Modifier.fillMaxWidth(),
                    onImageClick = { /* ... */ }
                )
            }
        }

    }
}

@Composable
private fun getBubbleWidth(): Dp {
    val windowSize = LocalWindowInfo.current.containerSize
    val density = LocalDensity.current

    return with(density) {
        (windowSize.width.toDp() * 0.8f)
    }
}