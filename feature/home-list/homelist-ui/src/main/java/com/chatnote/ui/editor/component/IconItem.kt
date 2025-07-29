package com.chatnote.ui.editor.component

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.chatnote.coreui.ui.component.CircularImage

@Composable
fun IconItem(
    iconUri: String,
    isSelected: Boolean,
    modifier: Modifier,
    onClick: () -> Unit
) {
    Box(modifier = modifier) {
        CircularImage(
            modifier = Modifier,
            iconSize = 64.dp,
            imageUri = iconUri,
            borderColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
            borderWidth = if (isSelected) {
                2.dp
            } else {
                1.dp
            },
            onClick = onClick
        )
    }
}