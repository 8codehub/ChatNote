package com.chatnote.ui.folderlist

import com.chatnote.common.analytics.AnalyticsTracker
import com.chatnote.coredomain.mapper.Mapper
import com.chatnote.coredomain.utils.AppPreferencesSync
import com.chatnote.coreui.arch.StatefulEventHandler
import com.chatnote.domain.model.DefaultFolder
import com.chatnote.domain.model.Folder
import com.chatnote.domain.model.Onboarding
import com.chatnote.domain.usecase.DeleteFolderUseCase
import com.chatnote.domain.usecase.GetFoldersUseCase
import com.chatnote.domain.usecase.GetOnboardingStatusUseCase
import com.chatnote.domain.usecase.InitializeDefaultFoldersUseCase
import com.chatnote.domain.usecase.PinFolderUseCase
import com.chatnote.domain.usecase.SetOnboardingStatusUseCase
import com.chatnote.domain.usecase.UnpinFolderUseCase
import com.chatnote.ui.folderlist.FolderListContract.FolderListEvent
import com.chatnote.ui.folderlist.FolderListContract.FolderListOneTimeEvent
import com.chatnote.ui.folderlist.FolderListContract.FolderListOneTimeEvent.ShowOnboarding
import com.chatnote.ui.folderlist.FolderListContract.FolderListState
import com.chatnote.ui.folderlist.FolderListContract.MutableFolderListState
import com.chatnote.ui.model.UiFolder
import com.google.android.play.core.review.ReviewManager
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject
import com.chatnote.content.R as CR


class FolderListStatefulEventHandler @Inject constructor(
    private val getFolders: GetFoldersUseCase,
    private val pinFolder: PinFolderUseCase,
    private val appPreferences: AppPreferencesSync,
    private val unpinFolder: UnpinFolderUseCase,
    private val reviewManager: ReviewManager,
    private val deleteFolder: DeleteFolderUseCase,
    private val getOnboardingStatus: GetOnboardingStatusUseCase,
    private val setOnboardingStatus: SetOnboardingStatusUseCase,
    private val initializeDefaultFolders: InitializeDefaultFoldersUseCase,
    private val folderToUiFolder: Mapper<Folder, UiFolder>,
    private val mapperResultErrorToErrorId: Mapper<Throwable?, Int>,
    private val analyticsTracker: AnalyticsTracker,
) : StatefulEventHandler<FolderListEvent, FolderListOneTimeEvent, FolderListState, MutableFolderListState>(
    FolderListState()
) {

    override suspend fun process(event: FolderListEvent, args: Any?) {
        when (event) {
            is FolderListEvent.LoadFolders -> {
                onLoadFoldersEvent()
            }

            is FolderListEvent.PinFolder -> {
                onPinFolderEvent(event.folderId)
            }

            is FolderListEvent.UnpinFolder -> {
                onUnpinFolderEvent(event.folderId)
            }

            is FolderListEvent.DeleteFolder -> {
                onDeleteFolderEvent(folderId = event.folderId)
            }

            is FolderListEvent.AskForOnboarding -> {
                onAskForOnboardingEvent(onboarding = event.onboarding)
            }

            is FolderListEvent.OnboardingFinished -> {
                setOnboardingStatus(onboarding = event.onboarding)
            }

            is FolderListEvent.GeneralError -> onGeneralError(throwable = event.throwable)
            is FolderListEvent.AddDefaultFolders -> onAddDefaultFoldersEvent(defaultFolders = event.defaultFolders)
        }
    }

    private suspend fun onAskForOnboardingEvent(onboarding: Onboarding) {
        val hasFolderOnboardingBeenShown = getOnboardingStatus(onboarding)
        if (!hasFolderOnboardingBeenShown) {
            ShowOnboarding(onboarding = onboarding).processOneTimeEvent()
        }
    }

    private suspend fun onAddDefaultFoldersEvent(defaultFolders: List<DefaultFolder>) {
        initializeDefaultFolders(defaultFolders = defaultFolders)
    }

    private suspend fun onGeneralError(throwable: Throwable) {
        analyticsTracker.trackGeneralError(
            message = throwable.message.orEmpty(),
            src = this.javaClass.name
        )
        FolderListOneTimeEvent.FailedOperation(error = mapperResultErrorToErrorId.map(throwable))
            .processOneTimeEvent()
    }

    private suspend fun onDeleteFolderEvent(folderId: Long) {
        deleteFolder(folderId = folderId).onSuccess { messagesCount ->
            analyticsTracker.trackFolderDeleted(folderId = folderId, messagesCount = messagesCount)
            FolderListOneTimeEvent.FolderDeleted(messagesCount = messagesCount)
                .processOneTimeEvent()
        }
    }

    private suspend fun onPinFolderEvent(folderId: Long) {
        pinFolder(folderId = folderId).onSuccess {
            analyticsTracker.trackFolderPinned(folderId = folderId, isPinned = true)
        }.onFailure {
            FolderListOneTimeEvent.FailedOperation(error = CR.string.error_folder_pin)
        }
    }

    private suspend fun onUnpinFolderEvent(folderId: Long) {
        unpinFolder(folderId = folderId).onSuccess {
            analyticsTracker.trackFolderPinned(folderId = folderId, isPinned = false)
        }.onFailure {
            FolderListOneTimeEvent.FailedOperation(error = CR.string.error_folder_unpin)
        }
    }

    private suspend fun onLoadFoldersEvent() {
        appPreferences.isFirstSession().let { firstStart ->
            analyticsTracker.trackAppStart(firstStart)
        }

        getFolders()
            .onStart {
                updateUiState { isLoading = true }
            }
            .onEach { folders -> processFolders(folders) }
            .catch { handleLoadingError() }
            .collect()
    }

    private suspend fun processFolders(folders: List<Folder>) {
        checkForFirstOpenEvent(folders)
        updateUiWithFolders(folders)
        checkIfUserIsReadyForReview(folders = folders)
    }

    private suspend fun checkIfUserIsReadyForReview(folders: List<Folder>) {
        folders.find { it.lastNote.isNotEmpty() }?.let {
            FolderListOneTimeEvent.AskForUserReview(
                reviewManager = reviewManager,
                analyticsTracker = analyticsTracker
            ).processOneTimeEvent()
        }
    }

    private suspend fun checkForFirstOpenEvent(folders: List<Folder>) {
        if (folders.isEmpty() && !appPreferences.isDefaultFoldersInitialized()) {
            FolderListOneTimeEvent.OnAppFirstOpen.processOneTimeEvent()
            appPreferences.markDefaultFoldersInitialized()
        }
    }

    private fun updateUiWithFolders(folders: List<Folder>) {
        updateUiState {
            isLoading = false
            this.folders = folderToUiFolder.mapList(folders)
            foldersCount = folders.size
        }
    }

    private fun handleLoadingError() {
        FolderListOneTimeEvent.FailedOperation(error = CR.string.error_failed_to_load_folders)
        updateUiState {
            isLoading = false
        }
    }
}