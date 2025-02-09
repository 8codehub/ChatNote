package com.chatnote.coredomain.utils

fun <T> Result.Companion.failure(error: ResultError): Result<T> {
    return failure(AppException(error))
}
