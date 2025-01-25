package com.pingpad.coredata.result

import com.pingpad.coredomain.utils.ResultError
import com.pingpad.coredomain.utils.AppException


fun <T> Result.Companion.resultError(error: ResultError): Result<T> {
    return failure(AppException(error))
}

inline fun <T> Result<T>.onAppException(
    errorHandler: (ResultError) -> Unit
): Result<T> {
    return this.onFailure { throwable ->
        if (throwable is AppException) {
            errorHandler(throwable.error)
        }
    }
}