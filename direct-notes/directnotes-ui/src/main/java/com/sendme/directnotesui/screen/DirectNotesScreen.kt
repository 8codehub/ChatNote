package com.sendme.directnotesui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pingpad.coreui.component.ui.component.StyledText
import com.sendme.directnotesui.R
import com.sendme.directnotesui.components.DirectNotesEmptyState
import com.sendme.directnotesui.components.DirectNotesTitle
import com.sendme.directnotesui.screen.editor.Editor
import com.sendme.directnotesui.viewmodel.DirectNotesViewModel
import com.sendme.directnotsdomain.SendMeNote
import com.sendme.navigation.NavigationRoute


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DirectNotesScreen(
    onBackClick: () -> Unit,
    navigateTo: (NavigationRoute) -> Unit,
    viewModel: DirectNotesViewModel = hiltViewModel()
) {
    val stateValue by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    DirectNotesTitle(
                        modifier = Modifier
                            .wrapContentSize()
                            .clickable {
                                navigateTo(NavigationRoute.FolderEditor(stateValue.folderId))
                            },
                        imageUri = stateValue.folderIconUri.orEmpty(),
                        notesCount = stateValue.notes.size,
                        folderName = stateValue.folderName
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_back),
                            tint = MaterialTheme.colorScheme.primary,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.background,
                    navigationIconContentColor = MaterialTheme.colorScheme.background,
                    actionIconContentColor = MaterialTheme.colorScheme.background
                )
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(
                        top = paddingValues.calculateTopPadding() // Use only the top padding
                    )
                    .fillMaxSize()
                    .imePadding()
            ) {
                if (stateValue.emptyNotes == true) {
                    DirectNotesEmptyState(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .padding(8.dp),
                        folderName = stateValue.folderName,
                        iconUri = stateValue.folderIconUri.orEmpty(),
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        reverseLayout = true
                    ) {
                        items(stateValue.notes.size) { index ->
                            NoteItem(note = stateValue.notes[index])
                        }
                    }
                }

                Editor(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(bottom = 8.dp),
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
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        StyledText(
            text = note.content,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .background(MaterialTheme.colorScheme.primary, shape = MaterialTheme.shapes.large)
                .padding(vertical = 8.dp, horizontal = 12.dp)

        )
    }
}