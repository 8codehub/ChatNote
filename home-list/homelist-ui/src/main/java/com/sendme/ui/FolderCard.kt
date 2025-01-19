package com.sendme.ui

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sendme.domain.model.Folder
import com.sendme.coreui.component.ui.component.StyledText
import com.sendme.coreui.component.ui.component.CircularImage

@Composable
fun FolderCard(
    folder: Folder,
    onClick: () -> Unit
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
            .padding(horizontal = 16.dp)
    ) {

        CircularImage(
            modifier = Modifier.padding(vertical = 4.dp),
            imageUrl = folder.iconUri.orEmpty(),
            iconSize = 56.dp,
         //   borderColor = MaterialTheme.colorScheme.primary,
            iconPadding = 0.dp,
           // imageColorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary),
         //   backgroundColor = MaterialTheme.colorScheme.t,
            borderWidth = 0.dp
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier) {
            StyledText(
                text = folder.name,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onBackground
            )

            StyledText(
                text = folder.lastNote,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.secondary
            )
        }

    }

}