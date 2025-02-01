package com.chatnote.coredomain.utils

class AppException(val error: ResultError) : Throwable(error.toString())
