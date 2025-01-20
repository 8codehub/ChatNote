package com.pingpad.coreui.arch

interface UiState

interface BaseState<S, M> {
    fun S.toMutable(): M
    fun M.toImmutable(): S
}
