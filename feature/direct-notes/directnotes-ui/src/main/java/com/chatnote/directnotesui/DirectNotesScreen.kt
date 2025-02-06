package com.chatnote.directnotesui

import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import chatnote.directnotesui.R
import com.chatnote.coreui.ui.decorations.showToast
import com.chatnote.directnotesui.actionablesheet.component.ActionableBottomSheet
import com.chatnote.directnotesui.directnoteslist.DirectNotesContract
import com.chatnote.directnotesui.directnoteslist.components.DirectNotesEmptyState
import com.chatnote.directnotesui.directnoteslist.components.DirectNotesTitle
import com.chatnote.directnotesui.directnoteslist.components.NoteItem
import com.chatnote.directnotesui.directnoteslist.components.editor.NoteEditorInput
import com.chatnote.directnotesui.model.UiActionableContent
import com.chatnote.navigation.NavigationRoute
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DirectNotesScreen(
    onBackClick: () -> Unit,
    navigateTo: (NavigationRoute) -> Unit,
    viewModel: DirectNotesViewModel = hiltViewModel()
) {
    val stateValue by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var actionableContent by remember { mutableStateOf<UiActionableContent?>(null) }

    LaunchedEffect(Unit) {
        viewModel.oneTimeEvent.collectLatest { event ->
            when (event) {
                is DirectNotesContract.DirectNotesOneTimeEvent.FailedOperation -> showToast(
                    context = context, context.getString(
                        chatnote.coreui.R.string.general_error
                    )
                )

                DirectNotesContract.DirectNotesOneTimeEvent.NavigateBack -> onBackClick()
                is DirectNotesContract.DirectNotesOneTimeEvent.NavigateTo -> navigateTo(event.route)
                is DirectNotesContract.DirectNotesOneTimeEvent.ShowActionableContentSheet -> {
                    actionableContent = event.uiActionableContent
                }
            }
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    DirectNotesTitle(
                        modifier = Modifier
                            .wrapContentSize()
                            .clickable { navigateTo(NavigationRoute.FolderEditor(stateValue.folderId)) },
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
                            contentDescription = null
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
                    .padding(top = paddingValues.calculateTopPadding())
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
                        iconUri = stateValue.folderIconUri.orEmpty()
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
                            NoteItem(
                                note = stateValue.notes[index],
                                onLongClick = { viewModel.onNoteLongClick(it) })
                        }
                    }
                }

                NoteEditorInput(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(bottom = 8.dp),
                    onNewNoteClick = { viewModel.addNote(it) }
                )
            }
        }
    )
    actionableContent?.let {
        ActionableBottomSheet(
            actionableContent = it,
            handleAction = { uiAction ->
                viewModel.handelAction(uiAction = uiAction)
            },
            onDismiss = { actionableContent = null }
        )
    }
}

