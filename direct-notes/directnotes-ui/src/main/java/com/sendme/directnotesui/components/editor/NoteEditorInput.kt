package com.sendme.directnotesui.components.editor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pingpad.coreui.component.ui.theme.PoppinsFontFamily
import com.pingpad.coreui.ui.component.CircularImage
import com.sendme.directnotesui.R

@Composable
fun NoteEditorInput(
    onNewNoteClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var newNote by remember { mutableStateOf(TextFieldValue("")) }

    Row(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
            )
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .heightIn(min = 40.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Text Input Field
        BasicTextField(
            value = newNote,
            onValueChange = { newNote = it },
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.Sentences
            ),
            modifier = Modifier
                .weight(1f)
                .background(
                    color = MaterialTheme.colorScheme.secondary,
                    shape = MaterialTheme.shapes.large
                )
                .padding(horizontal = 8.dp, vertical = 2.dp)
                .heightIn(min = 36.dp),
            textStyle = LocalTextStyle.current.copy(
                fontSize = 16.sp,
                fontFamily = PoppinsFontFamily,
                color = MaterialTheme.colorScheme.onSecondary
            ),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary)
        ) { innerTextField ->
            Row(
                modifier = Modifier.heightIn(36.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                innerTextField()
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        CircularImage(
            onClick = {
                if (newNote.text.isNotEmpty()) {
                    onNewNoteClick(newNote.text)
                    newNote = TextFieldValue("")
                }
            },
            drawableRes = R.drawable.ic_sent,
            iconSize = 24.dp,
            iconPadding = 6.dp,
        )
        Spacer(modifier = Modifier.width(16.dp))

    }
}
