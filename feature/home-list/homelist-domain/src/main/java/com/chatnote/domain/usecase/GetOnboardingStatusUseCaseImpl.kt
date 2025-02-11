package com.chatnote.domain.usecase

import com.chatnote.coredomain.utils.AppPreferencesSync
import com.chatnote.domain.model.Onboarding
import javax.inject.Inject

internal class GetOnboardingStatusUseCaseImpl @Inject constructor(
    private val appPreferences: AppPreferencesSync
) : GetOnboardingStatusUseCase {

    override suspend operator fun invoke(onboarding: Onboarding) = when (onboarding) {
        Onboarding.FolderOnboarding -> appPreferences.hasFolderOnboardingBeenShown()
    }

}