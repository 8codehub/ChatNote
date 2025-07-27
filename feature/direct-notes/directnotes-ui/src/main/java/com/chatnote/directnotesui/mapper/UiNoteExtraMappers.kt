package com.chatnote.directnotesui.mapper

import android.net.Uri
import com.chatnote.coredomain.mapper.Mapper
import com.chatnote.coredomain.models.NoteExtra
import com.chatnote.coredomain.models.NoteExtraType
import com.chatnote.directnotesui.model.UiNoteExtra
import javax.inject.Inject

class ImageUriToUiNoteExtraMapper @Inject constructor() :
    Mapper<Uri, UiNoteExtra> {

    override fun map(from: Uri) = UiNoteExtra.UiNoteImageExtra(uri = from.toString())
}

class UiNoteExtraToNoteExtraMapper @Inject constructor() :
    Mapper<UiNoteExtra, NoteExtra> {

    override fun map(from: UiNoteExtra): NoteExtra {
        return when (from) {
            is UiNoteExtra.UiNoteImageExtra -> NoteExtra(
                type = NoteExtraType.IMAGE,
                value = from.uri
            )
        }
    }
}
