package com.chatnote.data.repository

import com.chatnote.coredata.di.db.FolderDao
import com.chatnote.coredata.di.model.FolderWithLastNote
import com.chatnote.coredomain.facade.NotesRepositoryFacade
import com.chatnote.coredomain.mapper.Mapper
import com.chatnote.domain.model.Folder
import com.chatnote.domain.repository.FolderStreamRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FolderStreamRepositoryImpl @Inject constructor(
    private val folderDao: FolderDao,
    private val notesRepositoryFacade: NotesRepositoryFacade,
    private val folderWithLastNoteToFolder: Mapper<FolderWithLastNote, Folder>,
) : FolderStreamRepository {
    override fun getFolders(): Flow<List<Folder>> {
        return folderWithLastNoteToFolder.mapFlow(folderDao.observeFoldersWithLastNote())
    }
}