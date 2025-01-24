//package com.sendme.navigation
//
//sealed class UiEvent {
//    data object ShowLoading : UiEvent()
//    data object HideLoading : UiEvent()
//    data class ShowError(val message: String) : UiEvent()
//    data class Navigate(val route: NavigationRoute) : UiEvent()
//}