package com.pingpad.coreui.mapper

import com.pingpad.coredomain.mapper.Mapper
import com.pingpad.coredomain.utils.AppException
import com.pingpad.coredomain.utils.ResultError
import com.sendme.coreui.R
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
        }
    }
}
