package com.sendme.ui.folderlist.components

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
import com.pingpad.coreui.component.ui.component.CircularImage
import com.sendme.homelistui.R

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale

@Composable
fun FolderCard(
    folder: Folder,
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