package com.sendme.directnotesui.screen.editor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Editor(
    onNewNoteClick: (String) -> Unit,
    modifier: Modifier
) {
    var newNote by remember { mutableStateOf(TextFieldValue("")) }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            value = newNote,
            onValueChange = { newNote = it },
            modifier = Modifier
                .weight(1f)
                .background(Color.LightGray, shape = MaterialTheme.shapes.small)
                .padding(8.dp)
                .heightIn(min = 50.dp), // Fixed height for editor
            textStyle = LocalTextStyle.current.copy(fontSize = 16.sp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Button(
            onClick = {
                if (newNote.text.isNotEmpty()) {
                    onNewNoteClick(newNote.text)
                    newNote = TextFieldValue("")
                }
            },
            modifier = Modifier.height(50.dp)
        ) {
            Text(text = "Send")
        }
    }
}