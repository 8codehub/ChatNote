package com.sendme.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sendme.coreui.component.ui.component.CircularImage
import com.sendme.coreui.component.ui.component.StyledText
import com.sendme.homelistui.R
import com.sendme.navigation.NavigationRoute
import kotlinx.coroutines.delay

@OptIn(ExperimentalAnimationApi::class)
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

    // Manage the current hint index
    var currentHintIndex by remember { mutableIntStateOf(0) }

    // Automatically cycle through hints every 3 seconds
    LaunchedEffect(Unit) {
        while (true) {
            delay(3000L) // Change every 3 seconds
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
            modifier = Modifier.padding(vertical = 4.dp),
            drawableRes = R.drawable.ic_plus,
            backgroundColor = MaterialTheme.colorScheme.secondary,
            iconSize = 34.dp,
            iconPadding = 12.dp,
            borderWidth = 0.dp
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier) {
            // Title
            StyledText(
                text = stringResource(R.string.new_folder),
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onBackground
            )

            // Animated Hint (Subtitle)
            AnimatedContent(
                targetState = currentHintIndex,
                transitionSpec = {
                    slideInVertically { height -> height } + fadeIn() with
                            slideOutVertically { height -> -height } + fadeOut()
                }
            ) { index ->
                StyledText(
                    text = hints[index],
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    maxLines = 1, // Ensure single line
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}
