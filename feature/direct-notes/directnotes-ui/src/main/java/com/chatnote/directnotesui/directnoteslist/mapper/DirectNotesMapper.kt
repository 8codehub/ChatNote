package com.chatnote.directnotesui.directnoteslist.mapper

import com.chatnote.coredomain.mapper.Mapper
import com.chatnote.coreui.util.DateFormatter
import com.chatnote.directnotesdomain.model.Note
import com.chatnote.directnotesdomain.model.NoteActionType
import com.chatnote.directnotesdomain.model.NoteActionableContent
import com.chatnote.directnotesdomain.model.NoteActionableItem
import com.chatnote.directnotesui.model.UiActionableItem
import com.chatnote.directnotesui.model.UiNote
import com.chatnote.directnotesui.model.UiNoteActionableContent
import com.chatnote.directnotesui.model.UiNoteInteraction
import javax.inject.Inject

class NotesToUiNotesMapper @Inject constructor(private val dateFormatter: DateFormatter) :
    Mapper<Note, UiNote> {
    override fun map(from: Note) = UiNote(
        id = from.id,
        content = from.content,
        date = dateFormatter.formatLong(millis = from.createdAt),
        imagePaths = from.extras.map { it.value }
    )
}


class ActionableItemToUiActionableItemMapper @Inject constructor(
    private val noteActionTypeToUiNoteInteractionMapper: Mapper<NoteActionType, UiNoteInteraction>
) :
    Mapper<NoteActionableItem, UiActionableItem> {
    override fun map(from: NoteActionableItem) = UiActionableItem(
        content = from.content,
        actions = noteActionTypeToUiNoteInteractionMapper.mapList(from = from.actions)
            .sortedBy { it.order }
    )
}


class ActionTypeToUiActionTypeMapper @Inject constructor() :
    Mapper<NoteActionType, UiNoteInteraction> {
    override fun map(from: NoteActionType): UiNoteInteraction = when (from) {
        is NoteActionType.Call -> UiNoteInteraction.Call(phoneNumber = from.phoneNumber)
        is NoteActionType.Copy -> UiNoteInteraction.Copy(content = from.content)
        is NoteActionType.SMS -> UiNoteInteraction.SMS(phoneNumber = from.phoneNumber)
        is NoteActionType.Share -> UiNoteInteraction.Share(content = from.content)
        is NoteActionType.OpenWeb -> UiNoteInteraction.OpenWeb(url = from.url)
        is NoteActionType.OpenEmail -> UiNoteInteraction.OpenEmail(email = from.email)
    }
}

class ActionableContentToUiActionableContentMapper @Inject constructor(
    private val actionTypeToUiActionTypeMapper: Mapper<NoteActionableItem, UiActionableItem>
) :
    Mapper<NoteActionableContent, UiNoteActionableContent> {
    override fun map(from: NoteActionableContent) = UiNoteActionableContent(
        fullContent = from.fullContent,
        actionableItems = actionTypeToUiActionTypeMapper.mapList(from.noteActionableItems)
    )
}