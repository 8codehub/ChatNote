package com.sendme.domain.usecase

import com.sendme.domain.model.FolderIcon
import javax.inject.Inject

class GetFolderIconsUseCaseImpl @Inject constructor() : GetFolderIconsUseCase {

    override operator fun invoke(): List<FolderIcon> {
        return buildList {
            add(FolderIcon("account_balance"))
            add(FolderIcon("badge"))
            add(FolderIcon("checklist"))
            add(FolderIcon("family_home"))
            add(FolderIcon("folder"))
            add(FolderIcon("group"))
            add(FolderIcon("groups"))
            add(FolderIcon("new_folder"))
            add(FolderIcon("payments"))
            add(FolderIcon("person"))
            add(FolderIcon("fitness"))
        }
    }
}