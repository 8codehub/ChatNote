package com.chatnote.ui.folderlist

import androidx.annotation.StringRes
import com.chatnote.common.analytics.AnalyticsTracker
import com.chatnote.coreui.arch.ConvertibleState
import com.chatnote.coreui.arch.MutableConvertibleState
import com.chatnote.coreui.arch.UiEvent
import com.chatnote.coreui.arch.UiOneTimeEvent
import com.chatnote.domain.model.DefaultFolder
import com.chatnote.domain.model.Onboarding
import com.chatnote.ui.model.UiFolder
import com.google.android.play.core.review.ReviewManager
import com.chatnote.content.R as CR

object FolderListContract {

    // Immutable state
    data class FolderListState(
        override val isLoading: Boolean = true,
        val folders: List<UiFolder> = emptyList(),
        val foldersCount: Int? = null,
        val errorMessage: String? = null,
        @StringRes override val generalError: Int = CR.string.general_error,
    ) : ConvertibleState<FolderListState, MutableFolderListState> {

        override fun toMutable(): MutableFolderListState {
            return MutableFolderListState(
                isLoading = isLoading,
                folders = folders,
                errorMessage = errorMessage,
                foldersCount = foldersCount,
                generalError = generalError
            )
        }
    }

    // Mutable state
    class MutableFolderListState(
        override var isLoading: Boolean = false,
        var folders: List<UiFolder> = emptyList(),
        var errorMessage: String? = null,
        var foldersCount: Int? = null,
        @StringRes override var generalError: Int = CR.string.general_error,
    ) : MutableConvertibleState<FolderListState> {

        override fun toImmutable(): FolderListState {
            return FolderListState(
                isLoading = isLoading,
                folders = folders,
                errorMessage = errorMessage,
                foldersCount = foldersCount,
                generalError = generalError
            )
        }
    }

    // UI Events
    sealed class FolderListEvent : UiEvent {
        data object LoadFolders : FolderListEvent()
        data class AskForOnboarding(val onboarding: Onboarding) : FolderListEvent()
        data class OnboardingFinished(val onboarding: Onboarding) : FolderListEvent()
        data class PinFolder(val folderId: Long) : FolderListEvent()
        data class UnpinFolder(val folderId: Long) : FolderListEvent()
        data class DeleteFolder(val folderId: Long) : FolderListEvent()
        data class GeneralError(val throwable: Throwable) : FolderListEvent()
        data class AddDefaultFolders(val defaultFolders: List<DefaultFolder>) : FolderListEvent()
    }

    // One-Time Events
    sealed class FolderListOneTimeEvent : UiOneTimeEvent {

        data class FolderDeleted(val messagesCount: Int) :
            FolderListOneTimeEvent()

        data object OnAppFirstOpen : FolderListOneTimeEvent()

        data class AskForUserReview(
            val analyticsTracker: AnalyticsTracker,
            val reviewManager: ReviewManager
        ) : FolderListOneTimeEvent()

        data class FailedOperation(@StringRes val error: Int) : FolderListOneTimeEvent()

        data class ShowOnboarding(val onboarding: Onboarding) : FolderListOneTimeEvent()

    }
}
