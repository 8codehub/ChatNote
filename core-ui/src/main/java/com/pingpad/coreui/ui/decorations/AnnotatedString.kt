package com.pingpad.coreui.ui.decorations

import androidx.annotation.StringRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight

@Composable
fun getAnnotatedString(
    @StringRes baseStringRes: Int,
    annotatedValueColor: Color,
    annotatedValueFontWeight: FontWeight,
    valueToAnnotate: String?
): AnnotatedString {
    val valueToAnnotateOrEmpty = valueToAnnotate.orEmpty()
    val baseString = stringResource(baseStringRes, valueToAnnotateOrEmpty)
    val startIndex = baseString.indexOf(valueToAnnotateOrEmpty)
    val endIndex = startIndex + valueToAnnotateOrEmpty.length

    return buildAnnotatedString {
        append(baseString)
        if (startIndex in 0 until endIndex) {
            addStyle(
                style = SpanStyle(
                    color = annotatedValueColor,
                    fontWeight = annotatedValueFontWeight
                ),
                start = startIndex,
                end = endIndex
            )
        }
    }
}
