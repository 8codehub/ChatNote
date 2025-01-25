package com.sendme.ui.editor

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pingpad.coreui.ui.appbar.ContentDrivenTopBar
import com.pingpad.coreui.ui.component.CircularImage
import com.pingpad.coreui.ui.component.LabeledInputText
import com.pingpad.coreui.ui.component.StyledText
import com.pingpad.coreui.ui.decorations.showToast
import com.sendme.homelistui.R
import com.sendme.navigation.NavigationRoute
import com.sendme.ui.editor.component.IconItem

@Composable
fun FolderEditorScreen(
    viewModel: FolderEditorViewModel = hiltViewModel(),
    navigateTo: (NavigationRoute) -> Unit,
    onCancel: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val oneTimeEvent by viewModel.oneTimeEvent.collectAsStateWithLifecycle(null)
    val context = LocalContext.current
    var localFolderName by remember(state.folderName) { mutableStateOf(state.folderName ?: "") }
    var selectedIconUri by remember(key1 = state.folderIconUri, key2 = state.icons) {
        mutableStateOf(
            state.folderIconUri.takeUnless { it.isNullOrEmpty() }
                ?: state.icons.firstOrNull().orEmpty()
        )
    }

    LaunchedEffect(oneTimeEvent) {
        oneTimeEvent?.let {
            when (it) {
                is FolderEditorContract.FolderEditorOneTimeEvent.NavigateBack -> onCancel()
                is FolderEditorContract.FolderEditorOneTimeEvent.NavigateTo -> navigateTo(it.route)
                is FolderEditorContract.FolderEditorOneTimeEvent.ShowToast -> {}
                is FolderEditorContract.FolderEditorOneTimeEvent.FailedOperation -> showToast(
                    context = context,
                    context.getString(it.error)
                )
            }
        }
    }

    LaunchedEffect(localFolderName) {
        viewModel.onTextChanged()
    }

    Scaffold(
        modifier = Modifier,
        topBar = {
            ContentDrivenTopBar(
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
                    AnimatedVisibility(state.title != null) {
                        StyledText(
                            text = state.title?.let { stringResource(it) },
                            fontSize = 16.sp,
                            maxLines = 1,
                            fontWeight = FontWeight.W500,
                            overflow = TextOverflow.Ellipsis,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                },
                endContent = {
                    StyledText(
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .clickable {
                                viewModel.done(
                                    name = localFolderName,
                                    selectedIconUri = selectedIconUri
                                )
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
                imageUri = selectedIconUri,
                iconSize = 100.dp,
                iconPadding = 0.dp,
            )

            Spacer(modifier = Modifier.height(32.dp))

            LabeledInputText(
                modifier = Modifier.padding(horizontal = 12.dp),
                hint = stringResource(R.string.random),
                label = stringResource(R.string.name),
                clearTextIcon = R.drawable.ic_clear,
                text = localFolderName,
                inputError = state.inputError,
                onTextChange = {
                    localFolderName = it
                },
                onClearClick = {
                    localFolderName = ""
                },
            )

            if (state.icons.isNotEmpty()) {
                LazyVerticalGrid(
                    modifier = Modifier.padding(horizontal = 36.dp),
                    columns = GridCells.Fixed(4),
                    contentPadding = PaddingValues(vertical = 12.dp)
                ) {
                    items(state.icons.size) { index ->
                        val iconUri = state.icons[index]
                        IconItem(
                            modifier = Modifier.padding(bottom = 24.dp),
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

