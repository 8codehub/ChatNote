package com.chatnote.directnotesui.directnoteslist.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.chatnote.coreui.ui.component.StyledText
import com.chatnote.directnotesui.directnoteslist.NoteImagePager
import com.chatnote.directnotesui.model.UiNote

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteItem(
    note: UiNote,
    onLongClick: ((UiNote) -> Unit) = { }
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Column(
            modifier = Modifier
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
            horizontalAlignment = Alignment.End
        ) {
            StyledText(
                text = note.content,
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 18.sp,

                )

            if (note.imagePaths.isNotEmpty()) {
                NoteImagePager(imagePaths = note.imagePaths)
            }

            StyledText(
                text = note.date,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                maxLines = 1,
                fontStyle = FontStyle.Italic,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSecondary
            )
        }

    }
}