package com.pingpad.coreui.component.ui.decorations

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun AppHorizontalDivider(
    color: Color = MaterialTheme.colorScheme.secondary,
    thickness: Dp = 1.dp,
    modifier: Modifier = Modifier
) {
    HorizontalDivider(
        color = color,
        thickness = thickness,
        modifier = modifier
            .fillMaxWidth()
            .height(thickness)
            .then(Modifier.height(thickness))
    )
}