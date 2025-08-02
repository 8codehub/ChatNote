package com.chatnote.coreui.mapper

import com.chatnote.coredomain.mapper.Mapper
import com.chatnote.coredomain.utils.AppException
import com.chatnote.coredomain.utils.ResultError
import javax.inject.Inject
import com.chatnote.content.R as CR

internal class ResultErrorToErrorMessageMapper @Inject constructor() : Mapper<Throwable?, Int> {

    override fun map(from: Throwable?): Int {
        return when (from) {
            is AppException -> mapResultError(from.error)
            else -> CR.string.general_error
        }
    }

    private fun mapResultError(error: ResultError): Int {
        return when (error) {
            ResultError.EmptyFolderName -> CR.string.validation_error_empty_folder_name
            is ResultError.FolderNotFound -> CR.string.error_folder_not_found
            is ResultError.DatabaseError -> CR.string.error_database
            is ResultError.UnknownError -> CR.string.general_error
            is ResultError.DeleteFolderFail -> CR.string.folder_not_deleted
            is ResultError.NoteNotFound -> CR.string.error_note_not_found
            is ResultError.EmptyNoteContent -> CR.string.error_empty_note
            is ResultError.DeleteFileFail -> CR.string.error_file_not_found
        }
    }
}
