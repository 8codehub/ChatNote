package com.chatnote.coreui.mapper

import chatnote.coreui.R
import com.chatnote.coredomain.mapper.Mapper
import com.chatnote.coredomain.utils.AppException
import com.chatnote.coredomain.utils.ResultError
import javax.inject.Inject

internal class ResultErrorToErrorMessageMapper @Inject constructor() : Mapper<Throwable?, Int> {

    override fun map(from: Throwable?): Int {
        return when (from) {
            is AppException -> mapResultError(from.error)
            else -> R.string.general_error
        }
    }

    private fun mapResultError(error: ResultError): Int {
        return when (error) {
            ResultError.EmptyFolderName -> R.string.validation_error_empty_folder_name
            is ResultError.FolderNotFound -> R.string.error_folder_not_found
            is ResultError.DatabaseError -> R.string.error_database
            is ResultError.UnknownError -> R.string.general_error
            is ResultError.DeleteFolderFail -> R.string.folder_not_deleted
            is ResultError.NoteNotFound -> R.string.error_note_not_found
            is ResultError.EmptyNoteContent -> R.string.error_empty_note
        }
    }
}
