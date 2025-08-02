package com.chatnote.directnotesui.actionablesheet.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import chatnote.directnotesui.R
import com.chatnote.coreui.ui.component.CircularBackgroundIcon
import com.chatnote.coreui.ui.component.StyledText
import com.chatnote.coreui.ui.decorations.AppHorizontalDivider
import com.chatnote.directnotesui.model.UiNoteActionableContent
import com.chatnote.directnotesui.model.UiNoteInteraction
import com.chatnote.directnotesui.model.getActionStringResId
import com.chatnote.content.R as CR


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteInteractionBottomSheet(
    noteId: Long,
    uiNoteActionableContent: UiNoteActionableContent,
    handleAction: (UiNoteInteraction) -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            ActionableItemRow(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(CR.string.copy_entire_message),
                onContentClick = {
                    handleAction(
                        UiNoteInteraction.Copy(content = uiNoteActionableContent.fullContent)
                    )
                }
            )

            ActionableItemRow(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(CR.string.action_edit),
                onContentClick = {
                    onDismiss()
                    handleAction(
                        UiNoteInteraction.Edit(noteId = noteId)
                    )
                }
            )

            ActionableItemRow(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(CR.string.share),
                onContentClick = {
                    handleAction(
                        UiNoteInteraction.Share(content = uiNoteActionableContent.fullContent),
                    )
                }
            )

            ActionableItemRow(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(CR.string.delete),
                color = MaterialTheme.colorScheme.error,
                onContentClick = {
                    handleAction(
                        UiNoteInteraction.Delete(noteId = noteId)
                    )
                }
            )
            uiNoteActionableContent.actionableItems.forEach { item ->
                ActionableItemRow(
                    modifier = Modifier.fillMaxWidth(),
                    text = item.content,
                    actions = item.actions,
                    onAction = handleAction
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun ActionableItemRow(
    modifier: Modifier,
    text: String,
    color: Color = MaterialTheme.colorScheme.onBackground,
    onAction: ((UiNoteInteraction) -> Unit)? = null,
    onContentClick: (() -> Unit)? = null,
    actions: List<UiNoteInteraction> = emptyList(),
) {
    Column(
        modifier = modifier.then(if (onContentClick != null) {
            Modifier.clickable { onContentClick() }
        } else {
            Modifier
        })
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            StyledText(
                modifier = Modifier.weight(1f),
                text = text,
                color = color,
                maxLines = 1,
                fontSize = 16.sp,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 16.sp
            )

            if (actions.isNotEmpty()) {
                ActionIconsRow(
                    actions = actions,
                    onAction = onAction
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        AppHorizontalDivider()
    }
}

@Composable
fun ActionIconsRow(
    actions: List<UiNoteInteraction>,
    onAction: ((UiNoteInteraction) -> Unit)? = null
) {
    Row(
        modifier = Modifier,
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        actions.forEach { action ->
            CircularBackgroundIcon(
                contentDescription = stringResource(
                    id = action.getActionStringResId()
                ),
                iconSize = 18.dp,
                iconPadding = 2.dp,
                imageColorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary),
                onClick = { onAction?.let { it(action) } },
                drawableRes = getActionIcon(action)
            )
            Spacer(modifier = Modifier.width(4.dp))
        }
    }
}

@Composable
fun getActionIcon(action: UiNoteInteraction): Int {
    return when (action) {
        is UiNoteInteraction.Call -> R.drawable.ic_call
        is UiNoteInteraction.Copy -> R.drawable.ic_copy
        is UiNoteInteraction.SMS -> R.drawable.ic_sms
        is UiNoteInteraction.OpenEmail -> R.drawable.ic_email
        is UiNoteInteraction.OpenWeb -> R.drawable.ic_globe
        else -> R.drawable.ic_globe
    }
}
