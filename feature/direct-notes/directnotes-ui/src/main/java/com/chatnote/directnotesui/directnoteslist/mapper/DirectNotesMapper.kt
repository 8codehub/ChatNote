package com.chatnote.directnotesui.directnoteslist.mapper

import com.chatnote.coredomain.mapper.Mapper
import com.chatnote.coreui.util.DateFormatter
import com.chatnote.directnotesdomain.model.ActionType
import com.chatnote.directnotesdomain.model.ActionableItem
import com.chatnote.directnotesdomain.model.Note
import com.chatnote.directnotesdomain.model.NoteActionableContent
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
        date = dateFormatter.formatLong(millis = from.createdAt)
    )
}


class ActionableItemToUiActionableItemMapper @Inject constructor(
    private val actionTypeToUiNoteInteractionMapper: Mapper<ActionType, UiNoteInteraction>
) :
    Mapper<ActionableItem, UiActionableItem> {
    override fun map(from: ActionableItem) = UiActionableItem(
        content = from.content,
        actions = actionTypeToUiNoteInteractionMapper.mapList(from = from.actions)
            .sortedBy { it.order }
    )
}


class ActionTypeToUiActionTypeMapper @Inject constructor() : Mapper<ActionType, UiNoteInteraction> {
    override fun map(from: ActionType): UiNoteInteraction = when (from) {
        is ActionType.Call -> UiNoteInteraction.Call(phoneNumber = from.phoneNumber)
        is ActionType.Copy -> UiNoteInteraction.Copy(content = from.content)
        is ActionType.SMS -> UiNoteInteraction.SMS(phoneNumber = from.phoneNumber)
        is ActionType.Share -> UiNoteInteraction.Share(content = from.content)
        is ActionType.OpenWeb -> UiNoteInteraction.OpenWeb(url = from.url)
        is ActionType.OpenEmail -> UiNoteInteraction.OpenEmail(email = from.email)
    }
}

class ActionableContentToUiActionableContentMapper @Inject constructor(
    private val actionTypeToUiActionTypeMapper: Mapper<ActionableItem, UiActionableItem>
) :
    Mapper<NoteActionableContent, UiNoteActionableContent> {
    override fun map(from: NoteActionableContent) = UiNoteActionableContent(
        fullContent = from.fullContent,
        actionableItems = actionTypeToUiActionTypeMapper.mapList(from.actionableItems)
    )
}