package com.chatnote.directnotesui.directnoteslist.components.editor

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import chatnote.directnotesui.R
import com.chatnote.coreui.ui.component.CircularImage
import com.chatnote.coreui.ui.component.StyledText
import com.chatnote.coreui.ui.theme.PoppinsFontFamily
import com.chatnote.directnotesui.directnoteslist.DirectNotesContract
import com.chatnote.directnotesui.directnoteslist.components.editor.extra.NoteEditorExtras
import com.chatnote.directnotesui.model.UiNoteInteraction

@Composable
fun NoteEditorInput(
    noteExtrasState: DirectNotesContract.NoteExtrasState,
    modifier: Modifier = Modifier,
    onNewNoteClick: (String) -> Unit,
    onExtraClick: (UiNoteInteraction) -> Unit

) {
    var newNote by remember { mutableStateOf(TextFieldValue("")) }
    var showMenu by remember { mutableStateOf(false) }

    Column(modifier = modifier.padding(8.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .heightIn(min = 40.dp)
                .fillMaxWidth()
        ) {
            // Toggle button (arrow)
            IconButton(onClick = { showMenu = !showMenu }) {
                Icon(
                    imageVector = if (showMenu) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                    contentDescription = "Toggle menu"
                )
            }

            // Input
            BasicTextField(
                value = newNote,
                onValueChange = { newNote = it },
                keyboardOptions = KeyboardOptions.Default.copy(
                    capitalization = KeyboardCapitalization.Sentences
                ),
                modifier = Modifier
                    .weight(1f)
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = MaterialTheme.shapes.large
                    )
                    .padding(horizontal = 8.dp, vertical = 2.dp)
                    .heightIn(min = 36.dp),
                textStyle = LocalTextStyle.current.copy(
                    fontSize = 16.sp,
                    fontFamily = PoppinsFontFamily,
                    color = MaterialTheme.colorScheme.onSurface
                ),
                cursorBrush = SolidColor(MaterialTheme.colorScheme.primary)
            ) { innerTextField ->
                Row(
                    modifier = Modifier.heightIn(36.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box {
                        if (newNote.text.isEmpty()) {
                            StyledText(
                                textAlign = TextAlign.Start,
                                text = stringResource(chatnote.coreui.R.string.action_aa),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        innerTextField()
                    }
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Send button
            CircularImage(
                contentDescription = stringResource(R.string.action_create_note),
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
        }

        AnimatedVisibility(visible = showMenu) {
            NoteEditorExtras(onExtraClick = onExtraClick, noteExtrasState = noteExtrasState)
        }
    }
}

