package com.chatnote.coreui.ui.dialog

import androidx.annotation.StringRes
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.chatnote.coreui.ui.component.StyledText

@Composable
fun AppAlertDialog(
    showDialog: Boolean,
    message: String,
    @StringRes confirmButtonText: Int,
    @StringRes dismissButtonText: Int,
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
    title: String? = null,
    annotatedTitle: AnnotatedString? = null,
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            title = {
                StyledText(
                    text = title,
                    annotatedText = annotatedTitle,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            },
            text = {
                StyledText(
                    text = message,
                    fontSize = 16.sp
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onConfirm()
                        onDismissRequest()
                    }
                ) {
                    StyledText(
                        text = stringResource(confirmButtonText),
                        color = MaterialTheme.colorScheme.error,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onDismissRequest()
                    }
                ) {
                    StyledText(
                        text = stringResource(dismissButtonText),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        )
    }
}
