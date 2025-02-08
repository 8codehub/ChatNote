package com.chatnote.directnotesui.actionablesheet.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import chatnote.directnotesui.R
import com.chatnote.coreui.ui.component.CircularImage
import com.chatnote.coreui.ui.component.StyledText
import com.chatnote.coreui.ui.decorations.AppHorizontalDivider
import com.chatnote.directnotesui.model.UiActionableContent
import com.chatnote.directnotesui.model.UiActionableItem
import com.chatnote.directnotesui.model.UiNoteInteraction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActionableBottomSheet(
    uiActionableContent: UiActionableContent,
    handleAction: (UiNoteInteraction) -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            uiActionableContent.actionableItems.forEach { item ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        StyledText(
                            modifier = Modifier.weight(1f),
                            text = item.content,
                            maxLines = 1,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 16.sp,
                            overflow = TextOverflow.Ellipsis,
                            lineHeight = 16.sp
                        )

                        Row(
                            modifier = Modifier,
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            item.actions.forEach { action ->
                                CircularImage(
                                    imageColorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground),
                                    iconPadding = 4.dp,
                                    onClick = {
                                        handleAction(action)
                                    },
                                    drawableRes =
                                    when (action) {
                                        is UiNoteInteraction.Call -> R.drawable.ic_call
                                        is UiNoteInteraction.Copy -> R.drawable.ic_copy
                                        is UiNoteInteraction.SMS -> R.drawable.ic_sms
                                        is UiNoteInteraction.OpenEmail -> R.drawable.ic_email
                                        is UiNoteInteraction.OpenWeb -> R.drawable.ic_globe
                                        else -> R.drawable.ic_globe
                                    }
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    AppHorizontalDivider()
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            StyledText(
                modifier = Modifier.clickable {
                    handleAction(
                        UiNoteInteraction.Copy(content = uiActionableContent.fullContent)
                    )
                },
                text = stringResource(R.string.copy_entire_message),
                maxLines = 1,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 16.sp,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 16.sp
            )

            Spacer(modifier = Modifier.height(16.dp))
            AppHorizontalDivider()
            Spacer(modifier = Modifier.height(16.dp))

            StyledText(
                modifier = Modifier.clickable {
                    handleAction(
                        UiNoteInteraction.Share(content = uiActionableContent.fullContent),
                    )
                },
                text = stringResource(R.string.share),
                maxLines = 1,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 16.sp,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 16.sp
            )

            Spacer(modifier = Modifier.height(16.dp))
            AppHorizontalDivider()
            Spacer(modifier = Modifier.height(16.dp))

            StyledText(
                modifier = Modifier.clickable {
//                    handleAction(
//                        UiNoteInteraction.Share(content = actionableContent.fullContent)
//                    )
                },
                text = stringResource(R.string.delete),
                maxLines = 1,
                color = MaterialTheme.colorScheme.error,
                fontSize = 16.sp,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 16.sp
            )
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
