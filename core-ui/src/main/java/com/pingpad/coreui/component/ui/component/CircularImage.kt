package com.sendme.coreui.component.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage


@Composable
fun CircularImage(
    @DrawableRes drawableRes: Int,
    modifier: Modifier = Modifier,
    borderWidth: Dp = 0.dp,
    borderColor: Color = Color.Transparent,
    backgroundColor: Color = Color.Transparent, // Default background color
    contentScale: ContentScale = ContentScale.Crop, // To switch between Crop and Fill
    iconPadding: Dp = 0.dp, // Padding for the icon
    iconSize: Dp? = null, // Fixed size for the icon (optional)
    onClick: (() -> Unit)? = null // Optional click listener
) {
    Box(
        modifier = modifier
          //  .aspectRatio(1f)
            .clip(CircleShape)
            .background(backgroundColor)
            .border(
                width = borderWidth,
                color = borderColor,
                shape = CircleShape
            )
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier),
        contentAlignment = Alignment.Center // Center the icon within the circle
    ) {
        Box(
            modifier = Modifier
                .padding(iconPadding) // Add padding inside the circular background
                .clip(CircleShape)
        ) {
            Image(
                painter = painterResource(id = drawableRes),
                contentDescription = null,
                contentScale = contentScale, // Allows switching between Crop and Fill
                modifier = if (iconSize != null) Modifier.size(iconSize) else Modifier.wrapContentSize() // Wrap content if no size is provided
            )
        }
    }
}

@Composable
fun CircularImage(
    imageUrl: String,
    modifier: Modifier = Modifier,
    borderWidth: Dp = 0.dp,
    imageColorFilter: ColorFilter? = null,
    borderColor: Color = Color.Transparent,
    backgroundColor: Color = Color.Transparent, // Default background color
    contentScale: ContentScale = ContentScale.Crop, // To switch between Crop and Fill
    iconPadding: Dp = 0.dp, // Padding for the icon
    iconSize: Dp? = null, // Fixed size for the icon (optional)
    onClick: (() -> Unit)? = null // Optional click listener
) {
    Box(
        modifier = modifier
           // .aspectRatio(1f)
            .clip(CircleShape)
            .background(backgroundColor)
            .border(
                width = borderWidth,
                color = borderColor,
                shape = CircleShape
            )
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier),
        contentAlignment = Alignment.Center // Center the icon within the circle
    ) {
        Box(
            modifier = Modifier
                .padding(iconPadding) // Add padding inside the circular background
                .clip(CircleShape)
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                colorFilter = imageColorFilter,
                contentScale = contentScale, // Allows switching between Crop and Fill
                modifier = if (iconSize != null) Modifier.size(iconSize) else Modifier.wrapContentSize() // Wrap content if no size is provided
            )
        }
    }
}

@Composable
fun CircularBackgroundIcon(
    icon: ImageVector,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Transparent, // Default background color
    borderColor: Color = Color.Transparent, // Default border color
    borderWidth: Dp = 0.dp, // Default border width
    iconSize: Dp = 24.dp, // Default icon size
    backgroundSize: Dp = 48.dp, // Default background size
    iconPadding: Dp = 0.dp, // Padding around the icon
    onClick: (() -> Unit)? = null // Optional click listener
) {
    Box(
        modifier = modifier
            .size(backgroundSize) // Circular background size
            .aspectRatio(1f) // Ensure it remains circular
            .background(backgroundColor, shape = CircleShape) // Circular background
            .border(
                width = borderWidth,
                color = borderColor,
                shape = CircleShape
            )
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier),
        contentAlignment = Alignment.Center // Center the icon within the circular background
    ) {
        Image(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier
                .size(iconSize) // Size of the icon itself
                .padding(iconPadding), // Padding around the icon
        )
    }
}

@Composable
fun CircularBackgroundIcon(
    @DrawableRes drawableRes: Int,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Transparent, // Default background color
    borderColor: Color = Color.Transparent, // Default border color
    borderWidth: Dp = 0.dp, // Default border width
    iconSize: Dp = 24.dp, // Default icon size
    backgroundSize: Dp = 48.dp, // Default background size
    iconPadding: Dp = 0.dp, // Padding around the icon
    onClick: (() -> Unit)? = null // Optional click listener
) {
    Box(
        modifier = modifier
            .size(backgroundSize) // Circular background size
            .aspectRatio(1f) // Ensure it remains circular
            .background(backgroundColor, shape = CircleShape) // Circular background
            .border(
                width = borderWidth,
                color = borderColor,
                shape = CircleShape
            )
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier),
        contentAlignment = Alignment.Center // Center the icon within the circular background
    ) {
        androidx.compose.foundation.Image(
            painter = painterResource(id = drawableRes),
            contentDescription = null,
            modifier = Modifier
                .size(iconSize) // Size of the icon itself
                .padding(iconPadding) // Padding around the icon
        )
    }
}