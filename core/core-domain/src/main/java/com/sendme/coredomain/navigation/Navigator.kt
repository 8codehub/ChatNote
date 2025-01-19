package com.sendme.coredomain.navigation

import kotlinx.coroutines.channels.Channel

interface Navigator<T> {
    suspend fun navigate(action: T)
}
