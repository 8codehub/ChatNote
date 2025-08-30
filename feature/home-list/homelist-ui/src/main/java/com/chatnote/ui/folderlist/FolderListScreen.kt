package com.chatnote.ui.folderlist

import android.content.Context
import androidx.activity.compose.LocalActivity
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chatnote.coreui.extention.launchInAppReview
import com.chatnote.coreui.ui.component.LoadingComponent
import com.chatnote.coreui.ui.component.StyledText
import com.chatnote.coreui.ui.component.SwappableItem
import com.chatnote.coreui.ui.component.SwappableItemState
import com.chatnote.coreui.ui.decorations.getAnnotatedString
import com.chatnote.coreui.ui.decorations.showToast
import com.chatnote.coreui.ui.dialog.AppAlertDialog
import com.chatnote.coreui.util.PermissionType
import com.chatnote.coreui.util.permissionRequestLauncher
import com.chatnote.domain.model.Onboarding
import com.chatnote.navigation.NavigationRoute
import com.chatnote.ui.folderlist.FolderListContract.FolderListOneTimeEvent
import com.chatnote.ui.folderlist.FolderListContract.FolderListOneTimeEvent.FolderDeleted
import com.chatnote.ui.folderlist.components.AddNewFolderButton
import com.chatnote.ui.folderlist.components.FolderActionItems
import com.chatnote.ui.folderlist.components.FolderCard
import com.chatnote.ui.model.UiFolder
import kotlinx.coroutines.flow.collectLatest
import com.chatnote.content.R as CR


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FolderListScreen(
    viewModel: FolderListViewModel = hiltViewModel(),
    navigateTo: (NavigationRoute) -> Unit = {},
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var selectedFolderForDeletion by remember { mutableStateOf<UiFolder?>(null) }
    var onboardingItem by remember { mutableStateOf<Onboarding?>(null) }
    val context = LocalContext.current
    val activity = LocalActivity.current
    val lazyListState = rememberLazyListState()

    var swipeState by remember { mutableStateOf(SwappableItemState.Default) }
    val deleteFolderTitle = getAnnotatedString(
        baseStringRes = CR.string.delete_folder_title,
        valueToAnnotate = selectedFolderForDeletion?.name,
        annotatedValueColor = MaterialTheme.colorScheme.primary,
        annotatedValueFontWeight = FontWeight.Bold
    )

    val launcher = permissionRequestLauncher(
        type = PermissionType.NOTIFICATION,
        onGranted = { println("✅ Notifications allowed") },
        onDenied = { println("❌ Notifications denied") }
    )

    LaunchedEffect(Unit) {
        launcher.launch(PermissionType.NOTIFICATION.toSystemPermission())
    }

    LaunchedEffect(Unit) {
        viewModel.oneTimeEvent.collectLatest { oneTimeEvent ->
            when (oneTimeEvent) {
                is FolderDeleted -> onFolderDeletedOneTimeEvent(
                    context = context,
                    messagesCount = oneTimeEvent.messagesCount
                )

                is FolderListOneTimeEvent.FailedOperation -> showToast(
                    context = context,
                    message = context.getString(oneTimeEvent.error)
                )

                is FolderListOneTimeEvent.ShowOnboarding -> {
                    onboardingItem = oneTimeEvent.onboarding
                }

                FolderListOneTimeEvent.OnAppFirstOpen -> viewModel.onAppFirstOpen(
                    context = context
                )

                is FolderListOneTimeEvent.AskForUserReview -> {
                    activity?.launchInAppReview(
                        reviewManagerProvider = { oneTimeEvent.reviewManager },
                    )
                }
            }
        }

    }

    AppAlertDialog(
        showDialog = !selectedFolderForDeletion?.name.isNullOrEmpty(),
        onDismissRequest = { selectedFolderForDeletion = null },
        annotatedTitle = deleteFolderTitle,
        message = stringResource(CR.string.delete_folder_msg),
        confirmButtonText = CR.string.delete,
        dismissButtonText = CR.string.cancel,
        onConfirm = {
            viewModel.deleteFolder(folderId = selectedFolderForDeletion?.id)
        },
    )

    Scaffold(modifier = Modifier, topBar = {
        TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
            title = {
                state.foldersCount?.let {
                    StyledText(
                        color = MaterialTheme.colorScheme.onBackground,
                        text = stringResource(CR.string.folders_count, "$it"),
                        fontSize = 26.sp,
                        fontWeight = FontWeight.W700,
                    )
                }
            }
        )
    }) { innerPadding ->
        LoadingComponent(state.isLoading) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                contentPadding = innerPadding,
                state = lazyListState
            ) {
                item {
                    AddNewFolderButton(
                        modifier = Modifier, navigateTo = navigateTo
                    )
                }
                items(state.folders.size, key = { state.folders[it].id ?: 0 }) { index ->
                    val item = state.folders[index]

                    SwappableItem(
                        modifier = Modifier.animateItem(
                            fadeInSpec = null,
                            fadeOutSpec = null,
                            placementSpec = tween(300)
                        ),
                        showOnboarding = index == 0 && onboardingItem == Onboarding.FolderOnboarding,
                        onOnboardingFinished = {
                            onboardingItem = null
                            viewModel.onOnboardingFinished()
                        },
                        content = {
                            FolderCard(
                                modifier = Modifier.background(color = MaterialTheme.colorScheme.background),
                                folder = item,
                                onClick = {
                                    navigateTo(
                                        NavigationRoute.DirectNotes(
                                            folderId = item.id ?: 0,
                                        )
                                    )
                                })
                        }, onStateChange = { newState ->
                            swipeState = newState
                        },
                        swappableItemState = swipeState,
                        actionButtonsContent = {
                            FolderActionItems(
                                modifier = Modifier.padding(horizontal = 12.dp),
                                isPinned = item.isPinned,
                                onFolderEdit = {
                                    swipeState = SwappableItemState.Close
                                    navigateTo(NavigationRoute.FolderEditor(folderId = item.id))
                                },
                                onFolderPin = {
                                    swipeState = SwappableItemState.Close
                                    viewModel.pinFolder(folderId = item.id ?: 0)
                                },
                                onFolderUnPin = {
                                    swipeState = SwappableItemState.Close
                                    viewModel.unPinFolder(folderId = item.id ?: 0)
                                },
                                onFolderDelete = {
                                    swipeState = SwappableItemState.Close
                                    selectedFolderForDeletion = item
                                }
                            )
                        })

                }
            }
        }
    }
}

private fun onFolderDeletedOneTimeEvent(context: Context, messagesCount: Int) {
    when (messagesCount) {
        0 -> showToast(
            context = context, message = context.getString(CR.string.deleted_folder_with_no_message)
        )

        1 -> showToast(
            context = context,
            message = context.getString(CR.string.deleted_folder_message_singular)
        )

        else -> showToast(
            context = context,
            message = context.getString(CR.string.deleted_folder_message_plural, "$messagesCount")
        )
    }

}
