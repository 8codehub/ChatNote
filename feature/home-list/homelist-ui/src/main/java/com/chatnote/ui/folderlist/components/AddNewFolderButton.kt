package com.chatnote.ui.folderlist.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import chatnote.homelistui.R
import com.chatnote.coreui.ui.component.CircularImage
import com.chatnote.coreui.ui.component.StyledText
import com.chatnote.navigation.NavigationRoute
import kotlinx.coroutines.delay

@Composable
fun AddNewFolderButton(
    modifier: Modifier = Modifier,
    navigateTo: (NavigationRoute) -> Unit = {}
) {
    val hints = listOf(
        stringResource(R.string.hint_1),
        stringResource(R.string.hint_2),
        stringResource(R.string.hint_3)
    )

    var currentHintIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(3000L)
            currentHintIndex = (currentHintIndex + 1) % hints.size
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                navigateTo(NavigationRoute.FolderEditor())
            }
            .padding(horizontal = 16.dp)
    ) {

        CircularImage(
            contentDescription = stringResource(R.string.new_folder),
            modifier = Modifier.padding(vertical = 4.dp),
            drawableRes = R.drawable.ic_plus,
            backgroundColor = MaterialTheme.colorScheme.surface,
            imageColorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground),
            iconSize = 20.dp,
            iconPadding = 18.dp,
            borderWidth = 0.dp
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier) {
            StyledText(
                text = stringResource(R.string.new_folder),
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onBackground
            )

            AnimatedContent(
                targetState = currentHintIndex,
                transitionSpec = {
                    (slideInVertically { height -> height } + fadeIn()).togetherWith(
                        slideOutVertically { height -> -height } + fadeOut())
                }, label = ""
            ) { index ->
                StyledText(
                    text = hints[index],
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}
