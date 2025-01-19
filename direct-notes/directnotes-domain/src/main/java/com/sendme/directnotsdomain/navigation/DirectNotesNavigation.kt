package com.sendme.directnotsdomain.navigation

import com.sendme.coredomain.navigation.Navigator
import kotlinx.coroutines.channels.Channel

class DirectNotesNavigation <T> : Navigator<T> {
     val actions = Channel<T>(Channel.UNLIMITED)

    override suspend fun navigate(action: T) {
        actions.send(action)
    }
}