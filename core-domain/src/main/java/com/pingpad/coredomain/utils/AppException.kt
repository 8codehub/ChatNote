package com.pingpad.coredomain.utils

class AppException(val error: ResultError) : Throwable(error.toString())
