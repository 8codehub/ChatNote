package com.chatnote.coreui.util

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.core.app.ActivityCompat
import com.chatnote.coreui.ui.decorations.getAnnotatedString
import com.chatnote.coreui.ui.dialog.AppAlertDialog
import com.chatnote.content.R as CR


@Composable
fun permissionRequestLauncher(
    type: PermissionType,
    onGranted: () -> Unit,
    onDenied: (() -> Unit)? = null,
    onPermanentlyDenied: (() -> Unit)? = null,
    autoLaunch: Boolean = false,
    rationaleTitle: String? = null,
    rationaleMessage: String? = null
): ActivityResultLauncher<String> {
    val context = LocalContext.current
    val activity = context as? ComponentActivity

    var showPrePermissionDialog by remember { mutableStateOf(autoLaunch && rationaleMessage != null) }
    var showPermanentlyDeniedDialog by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        when {
            granted -> onGranted()
            activity != null &&
                    !ActivityCompat.shouldShowRequestPermissionRationale(
                        activity,
                        type.toSystemPermission()
                    ) -> {
                showPermanentlyDeniedDialog = true
                onPermanentlyDenied?.invoke()
            }

            else -> onDenied?.invoke()
        }
    }

    val systemPermission = type.toSystemPermission()

    LaunchedEffect(autoLaunch) {
        if (autoLaunch && rationaleMessage == null) {
            launcher.launch(systemPermission)
        }
    }

    // Step 1: Show rationale dialog if needed
    if (showPrePermissionDialog && rationaleMessage != null) {
        AppAlertDialog(
            showDialog = true,
            title = rationaleTitle,
            message = rationaleMessage,
            confirmButtonText = CR.string.allow,
            dismissButtonText = CR.string.cancel,
            onDismissRequest = { showPrePermissionDialog = false },
            onConfirm = {
                showPrePermissionDialog = false
                launcher.launch(systemPermission)
            }
        )
    }

    // Step 2: Show permanently denied dialog
    val permanentlyDeniedPermission = getAnnotatedString(
        baseStringRes = CR.string.permission_required_title,
        valueToAnnotate = type.name,
        annotatedValueColor = MaterialTheme.colorScheme.primary,
        annotatedValueFontWeight = FontWeight.Bold
    )

    if (showPermanentlyDeniedDialog) {
        AppAlertDialog(
            showDialog = true,
            message = "You’ve permanently denied the ${type.name.lowercase()} permission. Open settings to grant it manually.",
            confirmButtonText = CR.string.open_settings,
            dismissButtonText = CR.string.cancel,
            onDismissRequest = { showPermanentlyDeniedDialog = false },
            onConfirm = {
                showPermanentlyDeniedDialog = false
                context.startActivity(
                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", context.packageName, null)
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }
                )
            },
            annotatedTitle = permanentlyDeniedPermission
        )
    }

    return launcher
}

enum class PermissionType {
    CAMERA,
    GALLERY,
    NOTIFICATION;

    fun toSystemPermission(): String = when (this) {
        CAMERA -> Manifest.permission.CAMERA
        GALLERY -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            Manifest.permission.READ_MEDIA_IMAGES
        else
            Manifest.permission.READ_EXTERNAL_STORAGE

        NOTIFICATION -> Manifest.permission.POST_NOTIFICATIONS
    }
}