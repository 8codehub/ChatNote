package com.pingpad.coreui.component.ui.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.sendme.coreui.component.PoppinsFontFamily

@Composable
fun StyledText(
    text: String? = null,
    annotatedText: AnnotatedString? = null,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 16.sp,
    fontWeight: FontWeight = FontWeight.Normal,
    fontStyle: FontStyle = FontStyle.Normal,
    color: Color = MaterialTheme.colorScheme.onBackground,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip
) {
    annotatedText?.let {
        Text(
            text = it,
            modifier = modifier,
            fontSize = fontSize,
            fontWeight = fontWeight,
            fontStyle = fontStyle,
            color = color,
            lineHeight = lineHeight,
            textAlign = textAlign,
            fontFamily = PoppinsFontFamily,
            maxLines = maxLines,
            overflow = overflow
        )
    } ?: Text(
        text = text.orEmpty(),
        modifier = modifier,
        fontSize = fontSize,
        fontWeight = fontWeight,
        fontStyle = fontStyle,
        color = color,
        lineHeight = lineHeight,
        textAlign = textAlign,
        fontFamily = PoppinsFontFamily,
        maxLines = maxLines,
        overflow = overflow
    )
}