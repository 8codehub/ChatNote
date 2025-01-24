package com.pingpad.coreui.component.ui.decorations

import android.content.Context
import android.widget.Toast


fun onShowToastOneTimeEvent(message: String, context: Context) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}