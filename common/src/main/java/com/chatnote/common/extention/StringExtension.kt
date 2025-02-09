package com.chatnote.common.extention

fun String.truncate(maxLength: Int): String {
    return if (this.length > maxLength) this.take(maxLength) + "..." else this
}