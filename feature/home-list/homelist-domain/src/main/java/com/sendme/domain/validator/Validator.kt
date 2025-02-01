package com.sendme.domain.validator

interface NewFolderNameValidator {
    operator fun invoke(folderName: String?): Result<Unit>
}