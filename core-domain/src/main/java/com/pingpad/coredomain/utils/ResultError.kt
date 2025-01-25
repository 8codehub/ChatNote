package com.pingpad.coredomain.utils

sealed class ResultError {
    data object FolderNotFound : ResultError()
    data object EmptyFolderName : ResultError()

    data class DatabaseError(val th: Throwable? = null) : ResultError()
    data class DeleteFolderFail(val th: Throwable) : ResultError()
    data class UnknownError(val details: String) : ResultError()
}