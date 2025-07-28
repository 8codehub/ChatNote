package com.chatnote.directnotesui.directnoteslist.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun ExtraImagesGrid(
    imageUrls: List<String>,
    modifier: Modifier = Modifier,
    onImageClick: (String) -> Unit
) {
    Box(modifier = modifier) {
        when (imageUrls.size) {
            1 -> OneImage(imageUrls[0], onImageClick)
            2 -> TwoImages(imageUrls, onImageClick)
            3 -> ThreeImages(imageUrls, onImageClick)
            4 -> FourImages(imageUrls, onImageClick)
            else -> MoreThanFourImages(imageUrls, onImageClick)
        }
    }
}

@Composable
private fun OneImage(url: String, onClick: (String) -> Unit) {
    AsyncImage(
        model = url,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(6.dp))
            .clickable { onClick(url) }
    )
}

@Composable
private fun TwoImages(urls: List<String>, onClick: (String) -> Unit) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        urls.forEach { url ->
            AsyncImage(
                model = url,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(6.dp))
                    .clickable { onClick(url) }
            )
        }
    }
}

@Composable
private fun ThreeImages(urls: List<String>, onClick: (String) -> Unit) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        AsyncImage(
            model = urls[0],
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clip(RoundedCornerShape(6.dp))
                .clickable { onClick(urls[0]) }
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            urls.drop(1).forEach { url ->
                AsyncImage(
                    model = url,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                        .clip(RoundedCornerShape(6.dp))
                        .clickable { onClick(url) }
                )
            }
        }
    }
}

@Composable
private fun FourImages(urls: List<String>, onClick: (String) -> Unit) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        for (row in 0..1) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                for (col in 0..1) {
                    val index = row * 2 + col
                    AsyncImage(
                        model = urls[index],
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(6.dp))
                            .clickable { onClick(urls[index]) }
                    )
                }
            }
        }
    }
}

@Composable
private fun MoreThanFourImages(urls: List<String>, onClick: (String) -> Unit) {
    val firstFour = urls.take(4)
    val remaining = urls.size - 4

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        for (row in 0..1) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                for (col in 0..1) {
                    val index = row * 2 + col
                    val imageModifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(6.dp))
                        .clickable { onClick(urls[index]) }

                    if (index == 3) {
                        Box(modifier = imageModifier) {
                            AsyncImage(
                                model = firstFour[index],
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.Black.copy(alpha = 0.5f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "+$remaining",
                                    color = Color.White,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    } else {
                        AsyncImage(
                            model = firstFour[index],
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = imageModifier
                        )
                    }
                }
            }
        }
    }
}
