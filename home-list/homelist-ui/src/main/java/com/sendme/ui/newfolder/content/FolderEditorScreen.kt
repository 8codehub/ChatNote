package com.sendme.ui.newfolder.content

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sendme.coreui.component.ui.appbar.CustomTopBar
import com.sendme.coreui.component.ui.component.CircularImage
import com.sendme.coreui.component.ui.component.StyledText
import com.sendme.homelistui.R
import com.sendme.navigation.UiEvent
import com.sendme.ui.newfolder.viewmodel.FolderEditorViewModel

@Composable
fun FolderEditorScreen(
    viewModel: FolderEditorViewModel = hiltViewModel(),
    onCreateFolder: (UiEvent.Navigate) -> Unit,
    onCancel: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var selectedIconUri by remember { mutableStateOf(state.icons.firstOrNull().orEmpty()) }
    val navigateToNewFolder by viewModel.navigationEvent.collectAsStateWithLifecycle(null)

    LaunchedEffect(navigateToNewFolder) {
        navigateToNewFolder?.let {
            onCreateFolder(it)
        }
    }
    Scaffold(
        modifier = Modifier,
        topBar = {
            CustomTopBar(
                modifier = Modifier,
                startContent = {
                    StyledText(
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .clickable {
                                onCancel()
                            },
                        text = stringResource(R.string.cancel),
                        fontSize = 16.sp,
                        maxLines = 1,
                        fontWeight = FontWeight.W400,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                centerContent = {
                    StyledText(
                        text = stringResource(R.string.edit_details),
                        fontSize = 16.sp,
                        maxLines = 1,
                        fontWeight = FontWeight.W500,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                endContent = {
                    StyledText(
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .clickable {
                                viewModel.createNewFolder(name = state.folderName, selectedIconUri)
                            },
                        text = stringResource(R.string.done),
                        fontSize = 16.sp,
                        maxLines = 1,
                        fontWeight = FontWeight.W400,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
        ) {
            CircularImage(
                modifier = Modifier
                    .padding(top = 32.dp)
                    .align(Alignment.CenterHorizontally),
                imageUrl = selectedIconUri,
                iconSize = 100.dp,
                iconPadding = 0.dp,
            )

            Spacer(modifier = Modifier.height(32.dp))

            LabeledInputText(
                modifier = Modifier.padding(horizontal = 12.dp),
                hint = stringResource(R.string.random),
                label = stringResource(R.string.name),
                text = state.folderName,
                inputError = state.inputError,
                onTextChange = {
                    viewModel.onFolderNameChanged(it)
                },
                onClearClick = {
                    viewModel.onFolderNameChanged("")
                },
            )

            if (state.icons.isNotEmpty()) {
                LazyVerticalGrid(
                    modifier = Modifier.padding(horizontal = 36.dp),
                    columns = GridCells.Fixed(4),
                    contentPadding = PaddingValues(top = 24.dp) // Add general padding for the grid
                ) {
                    items(state.icons.size) { index ->
                        val iconUri = state.icons[index]
                        IconItem(
                            modifier = Modifier.padding(top = 24.dp),
                            iconUri = iconUri,
                            isSelected = iconUri == selectedIconUri,
                            onClick = {
                                selectedIconUri = iconUri
                            }
                        )
                    }
                }
            }

        }
    }


}

@Composable
fun IconItem(
    iconUri: String,
    isSelected: Boolean,
    modifier: Modifier,
    onClick: () -> Unit
) {
    Box(modifier = modifier) {
        CircularImage(
            modifier = Modifier,
            iconSize = 64.dp,
            imageUrl = iconUri,
            borderColor = if (isSelected) {
                MaterialTheme.colorScheme.primary
            } else {
                Color.Transparent
            },
            borderWidth = 2.dp,
            onClick = onClick
        )
    }
}