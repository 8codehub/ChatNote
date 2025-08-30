package com.chatnote.directnotesui.directnoteslist.components.editor.extra

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import chatnote.directnotesui.R
import coil.compose.AsyncImage
import com.chatnote.directnotesui.model.UiNoteExtra
import com.chatnote.content.R as CR

@Composable
fun SelectedNoteExtraItems(
    items: List<UiNoteExtra>,
    modifier: Modifier = Modifier,
    onRemoveExtraClicked: (UiNoteExtra) -> Unit,
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(items, key = { it.toString() }) { extraItem ->
            NoteExtraItem(extra = extraItem, onRemoveClick = { onRemoveExtraClicked(extraItem) })
        }

    }
}

@Composable
fun NoteExtraItem(
    extra: UiNoteExtra,
    onRemoveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.size(86.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(6.dp))
                .border(width = 1.dp, color = MaterialTheme.colorScheme.onBackground, shape = RoundedCornerShape(6.dp))

        ) {
            when (extra) {
                is UiNoteExtra.UiNoteImageExtra -> NoteImageExtra(extra)
            }
        }

        Box(
            modifier = Modifier
                .size(28.dp)
                .align(Alignment.TopEnd)
                .offset(y = (-9).dp, x = 8.dp)
                .clip(CircleShape)
                .clickable(
                    onClick = onRemoveClick,
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_remove_extra),
                contentDescription = "Remove",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}


@Composable
fun NoteImageExtra(extra: UiNoteExtra.UiNoteImageExtra) {
    AsyncImage(
        model = extra.uri,
        contentDescription = stringResource(CR.string.image),
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
    )
}