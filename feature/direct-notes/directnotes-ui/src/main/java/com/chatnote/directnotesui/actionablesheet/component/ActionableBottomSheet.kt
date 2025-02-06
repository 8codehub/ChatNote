package com.chatnote.directnotesui.actionablesheet.component

import androidx.compose.foundation.Image
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
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import chatnote.directnotesui.R
import com.chatnote.coreui.ui.component.StyledText
import com.chatnote.coreui.ui.decorations.AppHorizontalDivider
import com.chatnote.directnotesui.actionablesheet.action.UiAction
import com.chatnote.directnotesui.model.UiActionType
import com.chatnote.directnotesui.model.UiActionableContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActionableBottomSheet(
    actionableContent: UiActionableContent,
    handleAction: (UiAction) -> Unit,
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
            actionableContent.actionableItems.forEach { item ->
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
                                IconButton(onClick = {
                                    handleAction(UiAction(action = action, content = item.content))
                                }) {
                                    Image(
                                        painter = when (action) {
                                            UiActionType.Call -> painterResource(R.drawable.ic_call)
                                            UiActionType.Copy -> painterResource(R.drawable.ic_copy)
                                            UiActionType.SMS -> painterResource(R.drawable.ic_sms)
                                            UiActionType.OpenEmail -> painterResource(R.drawable.ic_email)
                                            UiActionType.OpenWeb -> painterResource(R.drawable.ic_globe)
                                            else -> painterResource(R.drawable.ic_globe)
                                        },
                                        contentDescription = action.toString()
                                    )
                                }
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
                        UiAction(
                            action = UiActionType.Copy,
                            content = actionableContent.fullContent
                        )
                    )
                },
                text = "Copy Entire Message",
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
                        UiAction(
                            action = UiActionType.Share,
                            content = actionableContent.fullContent
                        )
                    )
                },
                text = "Share",
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
                    onDismiss()
                },
                text = "Cancel",
                maxLines = 1,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 16.sp,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 16.sp
            )
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
