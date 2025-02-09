package com.chatnote.coreui.systemactions

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import com.chatnote.common.extention.truncate
import com.chatnote.coreui.ui.decorations.showToast
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ClipboardActionsImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : ClipboardActions {

    override fun copyToClipboard(text: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Copied Text", text)
        clipboard.setPrimaryClip(clip)
        showToast(context = context, message = text.truncate(15))
    }
}