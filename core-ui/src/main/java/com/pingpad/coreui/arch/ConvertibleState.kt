package com.pingpad.coreui.arch


interface ConvertibleState<S, M> {
    fun toMutable(): M
}

interface MutableConvertibleState<S> {
    fun toImmutable(): S
}