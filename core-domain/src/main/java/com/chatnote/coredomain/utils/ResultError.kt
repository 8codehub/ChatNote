package com.chatnote.coredomain.utils

sealed class ResultError {

    data object FolderNotFound : ResultError()
    data object EmptyFolderName : ResultError()

    data class DatabaseError(val throwable: Throwable? = null) : ResultError()
    data class DeleteFolderFail(val throwable: Throwable) : ResultError()
    data class UnknownError(val details: String) : ResultError()
}

fun ResultError.throwAsAppException(): Nothing {
    throw AppException(this)
}


