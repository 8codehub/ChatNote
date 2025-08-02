package com.chatnote.directnotesui.directnoteslist.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chatnote.coreui.ui.component.CircularImage
import com.chatnote.coreui.ui.component.StyledText
import com.chatnote.content.R as CR


@Composable
fun DirectNotesEmptyState(
    folderName: String,
    iconUri: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularImage(
            imageUri = iconUri,
            iconSize = 100.dp
        )
        StyledText(
            modifier = Modifier
                .wrapContentHeight()
                .padding(top = 12.dp),
            text = folderName,
            fontSize = 24.sp,
            maxLines = 1,
            lineHeight = 24.sp,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.W700
        )
        StyledText(
            modifier = Modifier
                .wrapContentHeight()
                .padding(top = 2.dp),
            text = stringResource(CR.string.empty_notes_message),
            fontSize = 14.sp,
            maxLines = 4,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.secondary,
            fontWeight = FontWeight.W400
        )
    }
}
