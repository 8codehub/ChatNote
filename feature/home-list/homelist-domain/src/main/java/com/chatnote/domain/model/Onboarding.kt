package com.chatnote.domain.model

sealed interface Onboarding {
    data object FolderOnboarding : Onboarding
}