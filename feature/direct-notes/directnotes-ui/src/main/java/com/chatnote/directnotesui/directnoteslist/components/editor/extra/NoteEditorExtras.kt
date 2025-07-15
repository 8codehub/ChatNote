package com.chatnote.directnotesui.directnoteslist.components.editor.extra

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import chatnote.directnotesui.R
import coil.compose.AsyncImage
import com.chatnote.directnotesui.directnoteslist.DirectNotesContract
import com.chatnote.directnotesui.model.UiNoteInteraction

@Composable
fun NoteEditorExtras(
    onExtraClick: (UiNoteInteraction) -> Unit,
    noteExtrasState: DirectNotesContract.NoteExtrasState
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (noteExtrasState.imagePaths.isNullOrEmpty()) {
            IconButton(onClick = {
                onExtraClick(UiNoteInteraction.ChooseImage)
            }) {
                Icon(
                    painter = painterResource(R.drawable.camera),
                    contentDescription = "Add image"
                )

            }
        } else {
            AsyncImage(
                model = noteExtrasState.imagePaths.firstOrNull(),
                contentDescription = "",
                modifier = Modifier
                    .width(100.dp)
                    .wrapContentHeight()
            )
        }


    }
}