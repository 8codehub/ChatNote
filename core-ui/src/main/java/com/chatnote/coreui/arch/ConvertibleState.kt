package com.chatnote.coreui.arch

interface ConvertibleState<S, M> {
    val generalError: Int
    val isLoading: Boolean
    fun toMutable(): M
}

interface MutableConvertibleState<S> {
    val generalError: Int
    var isLoading: Boolean
    fun toImmutable(): S
}