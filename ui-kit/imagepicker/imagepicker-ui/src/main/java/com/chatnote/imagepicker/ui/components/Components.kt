package com.chatnote.imagepicker.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.chatnote.coreui.ui.component.CircularImage
import com.chatnote.coreui.ui.component.StyledText
import com.chatnote.coreui.ui.theme.AppTheme
import com.chatnote.imagepicker.ui.R
import com.chatnote.content.R as CR


@Composable
internal fun CameraButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Column(
        modifier = modifier
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AsyncImage(
            modifier = Modifier.size(24.dp),
            model = R.drawable.ic_camera,
            contentScale = ContentScale.Fit,
            contentDescription = stringResource(CR.string.camera)
        )

        Spacer(modifier = Modifier.height(8.dp))

        StyledText(
            modifier = Modifier.wrapContentSize(),
            text = stringResource(CR.string.camera),
            fontSize = 14.sp,
            lineHeight = 14.sp,
            maxLines = 2,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
internal fun GalleryButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Column(
        modifier = modifier
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularImage(
            borderWidth = 0.dp,
            iconPadding = 8.dp,
            backgroundColor = MaterialTheme.colorScheme.primary,
            contentDescription = stringResource(CR.string.image),
            drawableRes = R.drawable.ic_galery,
            contentScale = ContentScale.Fit,
        )

        Spacer(modifier = Modifier.height(8.dp))

        StyledText(
            modifier = Modifier.wrapContentSize(),
            text = stringResource(CR.string.image),
            fontSize = 14.sp,
            lineHeight = 14.sp,
            maxLines = 2,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
internal fun RoundedAsyncImage(
    model: Any,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    onClick: () -> Unit

) {
    val borderColor = if (selected) MaterialTheme.colorScheme.primary else Color.Transparent
    AsyncImage(
        model = model,
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .size(80.dp)
            .clip(RoundedCornerShape(6.dp))
            .border(width = 4.dp, color = borderColor, shape = RoundedCornerShape(6.dp))
            .clickable { onClick() }
    )
}

@Preview(showBackground = true)
@Composable
internal fun CameraButtonPreview() {
    AppTheme {
        CameraButton(onClick = {})
    }
}