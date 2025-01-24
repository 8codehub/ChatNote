package com.sendme.ui.folderlist

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pingpad.coreui.component.ui.component.StyledText
import com.sendme.homelistui.R
import kotlinx.coroutines.delay

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun FolderCountTitle(
    folderCount: Int? = null,
    color: Color = MaterialTheme.colorScheme.onBackground,
    fontSize: TextUnit = 16.sp,
    fontWeight: FontWeight = FontWeight.Normal
) {
    // State to manage the displayed count
    var displayCount by remember { mutableStateOf(" ") }

    // Trigger animation when the folder count becomes available
    LaunchedEffect(folderCount) {
        if (folderCount != null) {
            delay(50L) // Delay of 3 seconds
            displayCount = folderCount.toString() // Update with the real count
        }
    }

    Row {
        // Static part (Folders)
        StyledText(
            text = stringResource(R.string.folders), // "Folders"
            color = color,
            fontSize = fontSize,
            fontWeight = fontWeight,
        )

        Spacer(modifier = Modifier.width(4.dp)) // Add spacing between "Folders" and count

        // Static part for "("
        StyledText(
            text = "(",
            color = color,
            fontSize = fontSize,
            fontWeight = fontWeight,
        )

        // Animated number
        AnimatedContent(
            targetState = displayCount,
            transitionSpec = {
                slideInVertically { it } + fadeIn() togetherWith slideOutVertically { -it } + fadeOut()
            }
        ) { count ->
            StyledText(
                text = count, // Only the animated number
                color = color,
                fontSize = fontSize,
                fontWeight = fontWeight,
            )
        }

        // Static part for ")"
        StyledText(
            text = ")",
            color = color,
            fontSize = fontSize,
            fontWeight = fontWeight,
        )
    }
}
