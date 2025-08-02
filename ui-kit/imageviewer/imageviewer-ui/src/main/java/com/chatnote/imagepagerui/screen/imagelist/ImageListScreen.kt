package com.chatnote.imagepagerui.screen.imagelist

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.chatnote.imagepagerui.components.ImagePagerTopAppBar
import com.chatnote.imagepagerui.navigation.PagerNavigationRoute
import com.chatnote.content.R as CR

@Composable
internal fun ImageListScreen(
    images: List<String>,
    onBackClick: () -> Unit,
    navigateTo: (PagerNavigationRoute) -> Unit = {},
) {
    Scaffold(
        topBar = {
            ImagePagerTopAppBar(
                count = images.size,
                onBackClick = onBackClick
            )
        }
    ) { paddingValues ->
        ImagePager(
            imageUrls = images,
            initialPage = 0,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            navigateTo = navigateTo

        )
    }
}

@Composable
internal fun ImagePager(
    imageUrls: List<String>,
    modifier: Modifier = Modifier,
    initialPage: Int = 0,
    navigateTo: (PagerNavigationRoute) -> Unit = {},
) {
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = initialPage)
    val flingBehavior: FlingBehavior = rememberSnapFlingBehavior(listState)
    LazyColumn(
        state = listState,
        flingBehavior = flingBehavior,
        modifier = modifier
    ) {
        items(imageUrls.size, key = { imageUrls[it] }) { index ->
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .clip(
                        MaterialTheme.shapes.large
                    )
                    .background(
                        color = MaterialTheme.colorScheme.onSecondary,
                        shape = MaterialTheme.shapes.large
                    )
            ) {
                AsyncImage(
                    model = imageUrls[index],
                    contentDescription = stringResource(CR.string.image),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(16.dp)
                        .height(460.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .clickable { navigateTo(PagerNavigationRoute.ImagePreview(imageUrls[index])) }
                )

            }
        }
    }
}