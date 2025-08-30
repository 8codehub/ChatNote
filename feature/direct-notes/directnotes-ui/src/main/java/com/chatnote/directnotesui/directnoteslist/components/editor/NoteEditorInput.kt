package com.chatnote.directnotesui.directnoteslist.components.editor

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import chatnote.directnotesui.R
import com.chatnote.coreui.ui.component.StyledText
import com.chatnote.coreui.ui.theme.PoppinsFontFamily
import com.chatnote.directnotesui.directnoteslist.DirectNotesContract
import com.chatnote.directnotesui.directnoteslist.components.editor.extra.NoteExtrasMenuItems
import com.chatnote.directnotesui.directnoteslist.components.editor.extra.SelectedNoteExtraItems
import com.chatnote.directnotesui.model.UiEditorInputAction
import com.chatnote.content.R as CR

@Composable
fun NoteEditorInput(
    noteExtrasState: DirectNotesContract.NoteExtrasState,
    modifier: Modifier = Modifier,
    onUiEditorInputAction: (UiEditorInputAction) -> Unit

) {
    var newNote by remember { mutableStateOf(TextFieldValue("")) }
    var showExtraMenu by remember { mutableStateOf(false) }
    val showSelectedExtras by remember(noteExtrasState.extras) {
        derivedStateOf { noteExtrasState.extras.isNotEmpty() }
    }

    Column(modifier = modifier.padding(8.dp)) {
        AnimatedVisibility(visible = showSelectedExtras) {
            Spacer(modifier = Modifier.height(8.dp))
            SelectedNoteExtraItems(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                items = noteExtrasState.extras
            ) {
                onUiEditorInputAction(UiEditorInputAction.RemoveExtra(it))
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .heightIn(min = 40.dp)
                .fillMaxWidth()
        ) {
            IconButton(onClick = { showExtraMenu = !showExtraMenu }) {
                Icon(
                    imageVector = if (showExtraMenu) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                    tint = MaterialTheme.colorScheme.onBackground,
                    contentDescription = "Toggle menu"
                )
            }

            BasicTextField(
                value = newNote,
                onValueChange = { newNote = it },
                keyboardOptions = KeyboardOptions.Default.copy(
                    capitalization = KeyboardCapitalization.Sentences
                ),
                modifier = Modifier
                    .weight(1f)
                    .background(
                        color = MaterialTheme.colorScheme.onSecondary,
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
                                text = stringResource(CR.string.action_aa),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        innerTextField()
                    }
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Box(modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .clickable {
                    onUiEditorInputAction(UiEditorInputAction.SaveNewNote(content = newNote.text))
                    newNote = TextFieldValue("")
                }) {
                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_send),
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(24.dp),
                    contentDescription = stringResource(CR.string.action_create_note),
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

        }

        AnimatedVisibility(visible = showExtraMenu) {
            NoteExtrasMenuItems(onExtraClick = {
                onUiEditorInputAction(it)
                showExtraMenu = false
            })
        }
    }
}

