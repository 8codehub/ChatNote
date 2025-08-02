package com.chatnote.directnotesui.directnoteslist.components.editor.extra

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chatnote.coreui.ui.component.CircularImage
import com.chatnote.coreui.ui.component.StyledText
import com.chatnote.directnotesui.model.UiEditorInputAction
import com.chatnote.imagepicker.ui.R
import com.chatnote.content.R as CR


@Composable
fun NoteExtrasMenuItems(
    onExtraClick: (UiEditorInputAction) -> Unit,
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        item {
            NoteExtraImageButton {
                onExtraClick(UiEditorInputAction.ChooseImage)
            }
        }
    }
}

@Composable
internal fun NoteExtraImageButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Column(
        modifier = modifier
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularImage(
            borderWidth = 0.dp,
            iconPadding = 8.dp,
            backgroundColor = MaterialTheme.colorScheme.primary,
            contentDescription = stringResource(CR.string.image),
            drawableRes = R.drawable.ic_galery,
            contentScale = ContentScale.Fit,
        )

        Spacer(modifier = Modifier.height(8.dp))

        StyledText(
            modifier = Modifier.wrapContentSize(),
            text = stringResource(CR.string.image),
            fontSize = 14.sp,
            lineHeight = 14.sp,
            maxLines = 2,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Medium
        )
    }

}