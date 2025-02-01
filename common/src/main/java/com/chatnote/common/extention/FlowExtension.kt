package com.chatnote.common.extention

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart

fun <T> Flow<T>.onFirstSubscription(action: suspend () -> Unit): Flow<T> {
    var isFirstEmission = true

    return onStart {
        if (isFirstEmission) {
            action()
            isFirstEmission = false
        }
    }
}
