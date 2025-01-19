package com.sendme.directnotesui

import com.sendme.directnotsdomain.SendMeNote

data class DirectNotesState(
    val folderName: String = "",
    val folderId: Long = 0,
    val notes: List<SendMeNote> = emptyList()
)