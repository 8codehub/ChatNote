package com.pingpad.coredomain.utils

fun <T> Result.Companion.failure(error: ResultError): Result<T> {
    return failure(AppException(error))
}

inline fun <T> Result<T>.onAppException(
    errorHandler: (ResultError) -> Unit
): Result<T> {
    return onFailure { throwable ->
        if (throwable is AppException) {
            errorHandler(throwable.error)
        }
    }
}
