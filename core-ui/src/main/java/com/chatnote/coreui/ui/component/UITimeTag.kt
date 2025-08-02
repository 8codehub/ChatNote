package com.chatnote.coreui.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chatnote.coreui.model.TimeTag

@Composable
fun UITimeTag(timeTag: TimeTag, date: String) {
    val backgroundColor = when (timeTag) {
        TimeTag.MORNING -> MaterialTheme.colorScheme.surfaceContainerLow
        TimeTag.AFTERNOON -> MaterialTheme.colorScheme.surfaceContainerHigh
        TimeTag.NIGHT -> MaterialTheme.colorScheme.surfaceContainerHighest
        TimeTag.EARTH_DAY -> MaterialTheme.colorScheme.surfaceContainerHigh
        TimeTag.CHRISTMAS,
        TimeTag.NEW_YEAR,
        TimeTag.HALLOWEEN,
        TimeTag.VALENTINES_DAY -> MaterialTheme.colorScheme.surfaceContainerHigh
    }
    val textColor = MaterialTheme.colorScheme.onBackground

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(backgroundColor)
            .padding(start = 4.dp, end = 2.dp, top = 2.dp, bottom = 2.dp)
    ) {

        StyledText(
            text = date,
            fontWeight = FontWeight.Medium,
            fontSize = 10.sp,
            lineHeight = 10.sp,
            maxLines = 1,
            fontStyle = FontStyle.Normal,
            overflow = TextOverflow.Ellipsis,
            color = textColor
        )
        StyledText(
            text = timeTag.emoji,
            fontWeight = FontWeight.Medium,
            fontSize = 10.sp,
            lineHeight = 10.sp,
            maxLines = 1,
            fontStyle = FontStyle.Normal,
            overflow = TextOverflow.Ellipsis,
            color = textColor
        )
    }
}