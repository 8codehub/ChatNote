package com.sendme.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sendme.coreui.component.ui.component.CircularBackgroundIcon
import com.sendme.coreui.component.ui.component.CircularImage
import com.sendme.coreui.component.ui.component.StyledText
import com.sendme.homelistui.R
import com.sendme.navigation.NavigationRoute
import com.sendme.navigation.UiEvent
import com.sendme.ui.viewmodel.HomeListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeListScreen(
    viewModel: HomeListViewModel = hiltViewModel(), // Inject ViewModel
    onFolderClick: (UiEvent.Navigate) -> Unit, onCreateFolderClick: () -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle() // Collect state from ViewModel

    val context = LocalContext.current

    Scaffold(modifier = Modifier, topBar = {
        TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),

            title = {
                StyledText(
                    color = MaterialTheme.colorScheme.onBackground,
                    text = "SendMe",
                    fontWeight = FontWeight.Bold
                )
            }, navigationIcon = {
                CircularBackgroundIcon(
                    modifier = Modifier.padding(start = 12.dp),
                    iconSize = 24.dp,
                    drawableRes = com.sendme.coreui.R.drawable.ic_logo,
                    borderWidth = 2.dp,
                    borderColor = MaterialTheme.colorScheme.primary
                )
            },

            actions = {
                IconButton(onClick = {
                    onCreateFolderClick()
                }) {
                    CircularImage(
                        drawableRes = R.drawable.ic_plus,
                        iconPadding = 6.dp,
                        backgroundColor = MaterialTheme.colorScheme.secondary,
                    )
                }
            }

        )
    }, floatingActionButton = {
        FloatingActionButton(onClick = {
            viewModel.createNewFolder(context.getString(R.string.new_folder))
        }) {
            Icon(
                imageVector = Icons.Filled.Add, contentDescription = "Add Folder"
            )
        }
    }) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            items(state.folders.size) { index ->
                val item = state.folders[index]
                FolderCard(folder = item, onClick = {
                    onFolderClick(
                        UiEvent.Navigate(
                            NavigationRoute.DirectNotes(
                                folderName = item.name,
                                folderId = item.id,
                                folderIconUri = item.iconUri.orEmpty()
                            )
                        )
                    )
                })
            }
        }
    }
}


