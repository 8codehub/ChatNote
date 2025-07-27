package com.chatnote.directnotesui.directnoteslist

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteImagePager(imagePaths: List<String>) {
    if (imagePaths.isEmpty()) return

    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        val pagerState = rememberPagerState(
            initialPage = 0,
            pageCount = { imagePaths.size }
        )

        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 24.dp), // adjust peek margin
            pageSpacing = 8.dp,
            pageSize = PageSize.Fixed(150.dp),
            modifier = Modifier
                .wrapContentWidth()
                .height(150.dp)
        ) { page ->
            AsyncImage(
                model = imagePaths[page],
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(150.dp)
                    .clip(MaterialTheme.shapes.medium)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        SmoothPagerIndicators(
            totalCount = imagePaths.size,
            currentPage = pagerState.currentPage,
            modifier = Modifier.wrapContentWidth()
        )
    }


}


@Composable
fun SmoothPagerIndicators(
    totalCount: Int,
    currentPage: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(totalCount) { index ->
            val isSelected = index == currentPage

            val indicatorColor by animateColorAsState(
                targetValue = if (isSelected) {
                    MaterialTheme.colorScheme.onPrimary
                } else {
                    MaterialTheme.colorScheme.onSurface
                },
                animationSpec = tween(durationMillis = 300),
                label = "indicatorColor"
            )

            Box(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(indicatorColor)
            )
        }
    }
}