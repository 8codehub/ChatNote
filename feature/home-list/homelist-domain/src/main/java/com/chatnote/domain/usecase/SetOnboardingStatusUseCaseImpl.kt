package com.chatnote.domain.usecase

import com.chatnote.coredomain.utils.AppPreferencesSync
import com.chatnote.domain.model.Onboarding
import javax.inject.Inject

internal class SetOnboardingStatusUseCaseImpl @Inject constructor(
    private val appPreferences: AppPreferencesSync
) : SetOnboardingStatusUseCase {

    override suspend operator fun invoke(onboarding: Onboarding) {
        when (onboarding) {
            Onboarding.FolderOnboarding -> appPreferences.markFolderOnboardingAsShown()
        }
    }

}