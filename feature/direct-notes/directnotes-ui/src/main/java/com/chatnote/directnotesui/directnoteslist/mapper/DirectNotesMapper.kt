package com.chatnote.directnotesui.directnoteslist.mapper

import com.chatnote.coredomain.mapper.Mapper
import com.chatnote.coreui.util.DateFormatter
import com.chatnote.directnotesdomain.model.ActionType
import com.chatnote.directnotesdomain.model.ActionableContent
import com.chatnote.directnotesdomain.model.ActionableItem
import com.chatnote.directnotesdomain.model.Note
import com.chatnote.directnotesui.model.UiActionType
import com.chatnote.directnotesui.model.UiActionableContent
import com.chatnote.directnotesui.model.UiActionableItem
import com.chatnote.directnotesui.model.UiNote
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
    private val actionTypeToUiActionTypeMapper: Mapper<ActionType, UiActionType>
) :
    Mapper<ActionableItem, UiActionableItem> {
    override fun map(from: ActionableItem) = UiActionableItem(
        content = from.content,
        actions = actionTypeToUiActionTypeMapper.mapList(from = from.actions).sortedBy { it.order }
    )
}


class ActionTypeToUiActionTypeMapper @Inject constructor() : Mapper<ActionType, UiActionType> {
    override fun map(from: ActionType): UiActionType = when (from) {
        ActionType.Call -> UiActionType.Call
        ActionType.Copy -> UiActionType.Copy
        ActionType.SMS -> UiActionType.SMS
        ActionType.Share -> UiActionType.Share
        ActionType.OpenWeb -> UiActionType.OpenWeb
        ActionType.OpenEmail -> UiActionType.OpenEmail
    }
}


class ActionableContentToUiActionableContentMapper @Inject constructor(
    private val actionTypeToUiActionTypeMapper: Mapper<ActionableItem, UiActionableItem>
) :
    Mapper<ActionableContent, UiActionableContent> {
    override fun map(from: ActionableContent) = UiActionableContent(
        fullContent = from.fullContent,
        actionableItems = actionTypeToUiActionTypeMapper.mapList(from.actionableItems)
    )
}