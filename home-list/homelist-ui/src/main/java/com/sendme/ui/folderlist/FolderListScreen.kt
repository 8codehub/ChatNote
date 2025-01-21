package com.sendme.ui.folderlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sendme.coreui.component.ui.component.CircularImage
import com.sendme.coreui.component.ui.component.StyledText
import com.sendme.coreui.component.ui.theme.SendMeTheme
import com.sendme.homelistui.R
import com.sendme.navigation.NavigationRoute
import com.sendme.ui.AddNewFolderButton
import com.sendme.ui.FolderCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FolderListScreen(
    viewModel: FolderListViewModel = hiltViewModel(), // Inject ViewModel
    navigateTo: (NavigationRoute) -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle() // Collect state from ViewModel


    Scaffold(modifier = Modifier, topBar = {
        TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
            title = {
                state.foldersCount?.let {
                    StyledText(
                        color = MaterialTheme.colorScheme.onBackground,
                        text = stringResource(R.string.folders_count, "$it"),
                        fontSize = 30.sp,
                        fontWeight = FontWeight.W700,
                    )
                }
            }
        )
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {

            LazyColumn() {
                item {
                    AddNewFolderButton(
                        modifier = Modifier, navigateTo = navigateTo
                    )
                }
                items(state.folders.size) { index ->
                    val item = state.folders[index]
                    FolderCard(folder = item, onClick = {
                        navigateTo(
                            NavigationRoute.DirectNotes(
                                folderName = item.name,
                                folderId = item.id,
                                folderIconUri = item.iconUri.orEmpty()
                            )
                        )
                    })
                }
            }
        }
    }
}


