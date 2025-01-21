package com.sendme.ui.folderlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.sendme.coreui.component.ui.component.CircularImage
import com.sendme.homelistui.R

@Composable
fun FolderActionItems(modifier: Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircularImage(
            iconSize = 18.dp,
            drawableRes = R.drawable.ic_edit,
            iconPadding = 6.dp,
            contentScale = ContentScale.Fit,

            backgroundColor = MaterialTheme.colorScheme.secondary
        )
        CircularImage(
            iconSize = 18.dp,

            drawableRes = R.drawable.ic_pin,
            iconPadding = 6.dp,
            contentScale = ContentScale.Fit,
            backgroundColor = MaterialTheme.colorScheme.secondary
        )
        CircularImage(
            iconSize = 18.dp,
            contentScale = ContentScale.Fit,

            drawableRes = R.drawable.ic_delete,
            iconPadding = 6.dp,
            backgroundColor = MaterialTheme.colorScheme.error
        )
    }
}