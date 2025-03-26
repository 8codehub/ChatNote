package com.chatnote.directnotesui.editnote

import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import chatnote.coreui.R.string
import chatnote.directnotesui.R
import com.chatnote.coreui.ui.appbar.ContentDrivenTopBar
import com.chatnote.coreui.ui.component.LabeledInputText
import com.chatnote.coreui.ui.component.StyledText
import com.chatnote.coreui.ui.decorations.showToast
import com.chatnote.directnotesui.editnote.EditNoteContract.EditNoteOneTimeEvent.FailedOperation
import com.chatnote.directnotesui.editnote.EditNoteContract.EditNoteOneTimeEvent.NavigateBack
import com.chatnote.directnotesui.editnote.EditNoteContract.EditNoteOneTimeEvent.NavigateTo
import com.chatnote.directnotesui.editnote.EditNoteContract.EditNoteOneTimeEvent.NoteUpdated
import com.chatnote.navigation.NavigationRoute
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@Composable
fun EditNoteScreen(
    viewModel: EditNoteViewModel = hiltViewModel(),
    navigateTo: (NavigationRoute) -> Unit,
    onCancel: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var currentNote by remember(state.note) { mutableStateOf(state.note) }

    LaunchedEffect(Unit) {
        viewModel.oneTimeEvent.collectLatest { event ->
            when (event) {
                is EditNoteContract.EditNoteOneTimeEvent.ShowToast -> {
                    showToast(context = context, message = event.message)
                }

                is FailedOperation -> showToast(
                    context = context,
                    context.getString(event.error)
                )

                is NavigateTo -> navigateTo(event.route)

                NoteUpdated -> onCancel()
                NavigateBack -> onCancel()
            }
        }
    }

    LaunchedEffect(currentNote?.content) {
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
                    StyledText(
                        text = stringResource(R.string.action_edit),
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
                                currentNote?.let { note ->
                                    viewModel.update(note)
                                }
                            },
                        text = stringResource(string.action_save),
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

            Spacer(modifier = Modifier.height(32.dp))

            LabeledInputText(
                modifier = Modifier
                    .padding(horizontal = 12.dp),
                hint = stringResource(string.action_aa),
                singleLine = false,
                text = currentNote?.content.orEmpty(),
                inputError = state.inputError,
                onTextChange = {
                    currentNote = currentNote?.copy(content = it)
                },
                onClearClick = {
                    currentNote = currentNote?.copy(content = "")
                },
            )

        }
    }

}

