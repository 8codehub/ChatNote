package com.chatnote.imagepagerui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.chatnote.coreui.ui.component.StyledText
import com.chatnote.content.R as CR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImagePagerTopAppBar(
    count: Int? = null,
    onBackClick: () -> Unit,
) {
    TopAppBar(
        title = {
            if (count != null) {
                ImagePagerTitle(count = count)
            }
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = ImageVector.vectorResource(chatnote.coreui.R.drawable.ic_back),
                    tint = MaterialTheme.colorScheme.onBackground,
                    contentDescription = "back"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.background,
            navigationIconContentColor = MaterialTheme.colorScheme.background,
            actionIconContentColor = MaterialTheme.colorScheme.background
        )
    )
}

@Composable
private fun ImagePagerTitle(
    count: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.wrapContentSize(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        StyledText(
            modifier = Modifier.wrapContentHeight(),
            text = stringResource(CR.string.images_with_count, count),
            fontSize = 16.sp,
            maxLines = 1,
            lineHeight = 16.sp,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.W700
        )
    }
}