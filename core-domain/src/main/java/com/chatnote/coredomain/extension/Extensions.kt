package com.chatnote.coredomain.extension

import android.net.Uri

fun Uri.getLastId(): String? {
    return this.lastPathSegment?.split('/')?.lastOrNull()
}
