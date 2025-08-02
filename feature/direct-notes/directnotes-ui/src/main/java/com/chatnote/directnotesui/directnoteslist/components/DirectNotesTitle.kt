package com.chatnote.directnotesui.directnoteslist.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chatnote.coreui.ui.component.CircularImage
import com.chatnote.coreui.ui.component.StyledText
import com.chatnote.content.R as CR


@Composable
fun DirectNotesTitle(
    imageUri: String,
    folderName: String,
    notesCount: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.wrapContentSize(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircularImage(
            imageUri = imageUri,
            borderWidth = 1.dp,
            borderColor = MaterialTheme.colorScheme.onBackground,
            iconSize = 36.dp
        )
        DirectNotesTitleText(
            folderName = folderName,
            notesCount = notesCount,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Composable
private fun DirectNotesTitleText(
    folderName: String,
    notesCount: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.wrapContentSize(),
        verticalArrangement = Arrangement.spacedBy(0.dp),
        horizontalAlignment = Alignment.Start
    ) {
        StyledText(
            modifier = Modifier.wrapContentHeight(),
            text = folderName,
            fontSize = 16.sp,
            maxLines = 1,
            lineHeight = 16.sp,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.W700
        )
        StyledText(
            modifier = Modifier.wrapContentHeight(),
            text = getNotesCountText(notesCount),
            fontSize = 12.sp,
            maxLines = 1,
            lineHeight = 12.sp,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.W500
        )
    }
}

@Composable
private fun getNotesCountText(notesCount: Int): String {
    return when (notesCount) {
        0 -> stringResource(CR.string.no_notes)
        1 -> stringResource(CR.string.one_note)
        else -> stringResource(CR.string.notes_count, notesCount)
    }
}
