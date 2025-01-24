package com.sendme.directnotesui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sendme.directnotesui.screen.editor.Editor
import com.sendme.directnotesui.viewmodel.DirectNotesViewModel
import com.sendme.directnotsdomain.SendMeNote


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DirectNotesScreen(
    onBackClick: () -> Unit,
    onOptionsClick: () -> Unit,
    viewModel: DirectNotesViewModel = hiltViewModel()
) {
    val stateValue by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stateValue.folderName, fontSize = 20.sp) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Outlined.KeyboardArrowLeft,
                            tint = MaterialTheme.colorScheme.primary,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onOptionsClick) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Options"
                        )
                    }
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(
                        top = paddingValues.calculateTopPadding() // Use only the top padding
                    )
                    .fillMaxSize()
                    .imePadding() // This must come last to adjust for the keyboard dynamically
            ) {
                // Messages Section
                LazyColumn(
                    modifier = Modifier
                        .weight(1f) // Fills available space
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp), // Ensure consistent padding
                    reverseLayout = true // Messages appear from the bottom
                ) {
                    items(stateValue.notes.size) { index ->
                        NoteItem(note = stateValue.notes[index])
                    }
                }

                // Input Section
                Editor(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding() // Ensures consistent spacing near navigation bars
                        .padding(8.dp), // Padding for input area)
                    onNewNoteClick = {
                        viewModel.addNote(note = it)
                    }
                )
            }
        }
    )
}

@Composable
fun NoteItem(note: SendMeNote) {
    Text(
        text = note.content,
        style = MaterialTheme.typography.bodySmall,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray, shape = MaterialTheme.shapes.small)
            .padding(8.dp)
    )
}