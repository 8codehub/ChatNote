package com.sendme.domain.usecase

import com.sendme.domain.model.FolderIcon
import javax.inject.Inject

class GetFolderIconsUseCaseImpl @Inject constructor() : GetFolderIconsUseCase {

    override operator fun invoke(): List<FolderIcon> {
        return listOf(
            FolderIcon("account_balance"),
            FolderIcon("badge"),
            FolderIcon("checklist"),
            FolderIcon("family_home"),
            FolderIcon("folder"),
            FolderIcon("group"),
            FolderIcon("groups"),
            FolderIcon("home"),
            FolderIcon("new_folder"),
            FolderIcon("payments"),
            FolderIcon("person"),
            FolderIcon("fitness")
        )
    }
}