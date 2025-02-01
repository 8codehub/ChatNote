package com.sendme.directnotesui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pingpad.coreui.ui.component.StyledText
import com.sendme.directnotesui.model.UiNote

@Composable
fun NoteItem(note: UiNote) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 4.dp)
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