package com.chatnote.directnotesui.directnoteslist.components

import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chatnote.coreui.ui.component.StyledText
import com.chatnote.directnotesui.model.NoteTimeTag
import com.chatnote.directnotesui.model.UiNote

@Composable
fun NoteItem(
    isFirstItem: Boolean,
    note: UiNote,
    onLongClick: (UiNote) -> Unit = { },
) {
    val bubbleWidth = getBubbleWidth()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 4.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .widthIn(max = bubbleWidth)
                .combinedClickable(
                    onClick = { },
                    onLongClick = { onLongClick(note) }
                )
                .background(
                    color = MaterialTheme.colorScheme.onSecondary,
                    shape = if (isFirstItem) {
                        RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp,
                            bottomStart = 16.dp,
                            bottomEnd = 0.dp
                        )
                    } else {
                        MaterialTheme.shapes.large
                    }
                )
                .padding(horizontal = 16.dp),
        ) {
            if (note.content.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                StyledText(
                    modifier = Modifier
                        .wrapContentWidth()
                        .align(Alignment.Start),
                    text = note.content,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 16.sp,
                    lineHeight = 16.sp
                )
            }

            if (note.imagePaths.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                ExtraImagesGrid(
                    imageUrls = note.imagePaths,
                    modifier = Modifier.height(bubbleWidth),
                    onImageClick = { /* ... */ },
                    onLongClick = { onLongClick(note) }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            NoteDateTag(timeTag = note.timeTag, date = note.date)
            Spacer(modifier = Modifier.height(8.dp))
        }

    }
}

@Composable
fun NoteDateTag(timeTag: NoteTimeTag, date: String) {
    val backgroundColor = when (timeTag) {
        NoteTimeTag.MORNING -> MaterialTheme.colorScheme.surfaceContainerLow
        NoteTimeTag.AFTERNOON -> MaterialTheme.colorScheme.surfaceContainerHigh
        NoteTimeTag.NIGHT -> MaterialTheme.colorScheme.surfaceContainerHighest
        NoteTimeTag.EARTH_DAY -> MaterialTheme.colorScheme.surfaceContainerHigh
        NoteTimeTag.CHRISTMAS,
        NoteTimeTag.NEW_YEAR,
        NoteTimeTag.HALLOWEEN,
        NoteTimeTag.VALENTINES_DAY -> MaterialTheme.colorScheme.surfaceContainerHigh
    }
    val textColor = MaterialTheme.colorScheme.onBackground

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(backgroundColor)
            .padding(horizontal = 4.dp, vertical = 2.dp)
    ) {

        StyledText(
            text = date,
            fontWeight = FontWeight.Medium,
            fontSize = 10.sp,
            lineHeight = 10.sp,
            maxLines = 1,
            fontStyle = FontStyle.Normal,
            overflow = TextOverflow.Ellipsis,
            color = textColor
        )
        StyledText(
            text = timeTag.emoji,
            fontWeight = FontWeight.Medium,
            fontSize = 10.sp,
            lineHeight = 10.sp,
            maxLines = 1,
            fontStyle = FontStyle.Normal,
            overflow = TextOverflow.Ellipsis,
            color = textColor
        )
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