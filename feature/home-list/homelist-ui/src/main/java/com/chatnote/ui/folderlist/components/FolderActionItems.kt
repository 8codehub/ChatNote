package com.chatnote.ui.folderlist.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import chatnote.homelistui.R
import com.chatnote.coreui.ui.component.CircularImage

@Composable
fun FolderActionItems(
    isPinned: Boolean,
    modifier: Modifier,
    onFolderPin: () -> Unit = {},
    onFolderUnPin: () -> Unit = {},
    onFolderEdit: () -> Unit = {},
    onFolderDelete: () -> Unit = {},
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircularImage(
            onClick = onFolderEdit,
            iconSize = 18.dp,
            drawableRes = R.drawable.ic_edit,
            iconPadding = 6.dp,
            contentScale = ContentScale.Fit,
            backgroundColor = MaterialTheme.colorScheme.secondary
        )

        CircularImage(
            iconSize = 18.dp,
            onClick = if (isPinned) {
                onFolderUnPin
            } else {
                onFolderPin
            },
            drawableRes = R.drawable.ic_pin,
            iconPadding = 6.dp,
            imageColorFilter = if (isPinned) {
                ColorFilter.tint(MaterialTheme.colorScheme.onPrimary)
            } else {
                null
            },
            contentScale = ContentScale.Fit,
            backgroundColor = if (isPinned) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.secondary
            }
        )

        CircularImage(
            iconSize = 18.dp,
            contentScale = ContentScale.Fit,
            onClick = onFolderDelete,
            drawableRes = R.drawable.ic_delete,
            iconPadding = 6.dp,
            backgroundColor = MaterialTheme.colorScheme.error
        )
    }
}