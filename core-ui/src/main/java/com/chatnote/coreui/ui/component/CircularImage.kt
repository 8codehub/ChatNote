package com.chatnote.coreui.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun CircularImage(
    @DrawableRes drawableRes: Int,
    modifier: Modifier = Modifier,
    iconPadding: Dp = 0.dp,
    iconSize: Dp? = null,
    borderWidth: Dp = 0.dp,
    imageColorFilter: ColorFilter? = null,
    borderColor: Color = Color.Transparent,
    backgroundColor: Color = Color.Transparent,
    contentScale: ContentScale = ContentScale.Crop,
    onClick: (() -> Unit)? = null
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(backgroundColor)
            .border(width = borderWidth, color = borderColor, shape = CircleShape)
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .padding(iconPadding)
                .clip(CircleShape)
        ) {
            Image(
                painter = painterResource(id = drawableRes),
                contentDescription = null,
                colorFilter = imageColorFilter,
                contentScale = contentScale,
                modifier = if (iconSize != null) Modifier.size(iconSize) else Modifier.wrapContentSize()
            )
        }
    }
}

@Composable
fun CircularImage(
    imageUri: String,
    modifier: Modifier = Modifier,
    iconPadding: Dp = 0.dp,
    iconSize: Dp? = null,
    borderWidth: Dp = 0.dp,
    imageColorFilter: ColorFilter? = null,
    borderColor: Color = Color.Transparent,
    backgroundColor: Color = Color.Transparent,
    contentScale: ContentScale = ContentScale.Crop,
    onClick: (() -> Unit)? = null
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(backgroundColor)
            .border(width = borderWidth, color = borderColor, shape = CircleShape)
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .padding(iconPadding)
                .clip(CircleShape)
        ) {
            AsyncImage(
                model = imageUri,
                contentDescription = null,
                colorFilter = imageColorFilter,
                contentScale = contentScale,
                modifier = if (iconSize != null) Modifier.size(iconSize) else Modifier.wrapContentSize()
            )
        }
    }
}

@Composable
fun CircularBackgroundIcon(
    @DrawableRes drawableRes: Int,
    modifier: Modifier = Modifier,
    iconPadding: Dp = 0.dp,
    iconSize: Dp = 24.dp,
    borderWidth: Dp = 0.dp,
    imageColorFilter: ColorFilter? = null,
    borderColor: Color = Color.Transparent,
    backgroundColor: Color = Color.Transparent,
    onClick: (() -> Unit)? = null
) {
    Box(
        modifier = modifier
            .wrapContentSize(unbounded = true)
            .background(backgroundColor, shape = CircleShape)
            .border(width = borderWidth, color = borderColor, shape = CircleShape)
            .padding(iconPadding)
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = drawableRes),
            colorFilter = imageColorFilter,
            contentDescription = null,
            modifier = Modifier
                .size(iconSize)
        )
    }
}
