package com.chatnote.directnotesui.mapper

import com.chatnote.coredomain.mapper.Mapper
import com.chatnote.coreui.model.SystemActionType
import com.chatnote.directnotesui.model.UiNoteInteraction
import javax.inject.Inject


class UiNoteInteractionToSystemActionTypeMapper @Inject constructor() :
    Mapper<UiNoteInteraction, SystemActionType> {

    override fun map(from: UiNoteInteraction) = when (from) {
        is UiNoteInteraction.Call -> SystemActionType.Call(phoneNumber = from.phoneNumber)
        is UiNoteInteraction.Copy -> SystemActionType.Copy(content = from.content)
        is UiNoteInteraction.SMS -> SystemActionType.SMS(phoneNumber = from.phoneNumber)
        is UiNoteInteraction.Share -> SystemActionType.Share(content = from.content)
        is UiNoteInteraction.OpenWeb -> SystemActionType.OpenWeb(url = from.url)
        is UiNoteInteraction.OpenEmail -> SystemActionType.SendEmail(email = from.email)
        else -> SystemActionType.NotSupported
    }
}
