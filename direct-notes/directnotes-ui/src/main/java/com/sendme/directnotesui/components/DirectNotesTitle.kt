package com.sendme.directnotesui.components

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
import com.pingpad.coreui.component.ui.component.StyledText
import com.sendme.coreui.component.ui.component.CircularImage
import com.sendme.directnotesui.R

@Composable
fun DirectNotesTitle(
    imageUri: String,
    folderName: String,
    notesCount: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.wrapContentSize(), // Ensure the row wraps its content
        verticalAlignment = Alignment.CenterVertically // Center-align the icon and text
    ) {
        CircularImage(
            imageUri = imageUri,
            iconSize = 36.dp // Ensure consistent icon size
        )
        DirectNotesTitleText(
            modifier = Modifier.padding(start = 8.dp), // Add spacing between the icon and text
            folderName = folderName,
            notesCount = notesCount
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
        modifier = modifier.wrapContentSize(), // Ensure the column wraps its content
        verticalArrangement = Arrangement.spacedBy(0.dp), // Reduce spacing between title and subtitle
        horizontalAlignment = Alignment.Start // Align text to the start
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
            text = stringResource(R.string.notes_count, notesCount),
            fontSize = 12.sp,
            maxLines = 1,
            lineHeight = 12.sp,
            color = MaterialTheme.colorScheme.secondary,
            fontWeight = FontWeight.W500
        )
    }
}