package com.sendme.ui.folderlist.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pingpad.coreui.ui.component.CircularImage
import com.pingpad.coreui.ui.component.StyledText
import com.sendme.homelistui.R
import com.sendme.ui.model.UiFolder

@Composable
fun FolderCard(
    folder: UiFolder,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
            .padding(horizontal = 16.dp)
    ) {

        CircularImage(
            modifier = Modifier.padding(vertical = 4.dp),
            imageUri = folder.iconUri.orEmpty(),
            iconSize = 56.dp,
            iconPadding = 0.dp,
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

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                StyledText(
                    modifier = Modifier.weight(1f, fill = false),
                    text = folder.lastNote.ifEmpty {
                        stringResource(R.string.empty_folder_hint)
                    },
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontStyle = if (folder.lastNote.isEmpty()) {
                        FontStyle.Italic
                    } else {
                        FontStyle.Normal
                    },
                    color = MaterialTheme.colorScheme.secondary
                )

                Spacer(modifier = Modifier.width(4.dp))
                StyledText(
                    text = folder.lastNoteCreatedDate,
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    maxLines = 1,
                    fontStyle = FontStyle.Italic,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
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