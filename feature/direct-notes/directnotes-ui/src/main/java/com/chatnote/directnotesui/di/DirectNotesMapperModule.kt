package com.chatnote.directnotesui.di

import com.chatnote.coredomain.mapper.Mapper
import com.chatnote.coreui.model.SystemActionType
import com.chatnote.coreui.util.DateFormatter
import com.chatnote.directnotesdomain.model.Note
import com.chatnote.directnotesdomain.model.NoteActionType
import com.chatnote.directnotesdomain.model.NoteActionableContent
import com.chatnote.directnotesdomain.model.NoteActionableItem
import com.chatnote.directnotesui.directnoteslist.mapper.ActionTypeToUiActionTypeMapper
import com.chatnote.directnotesui.directnoteslist.mapper.ActionableContentToUiActionableContentMapper
import com.chatnote.directnotesui.directnoteslist.mapper.ActionableItemToUiActionableItemMapper
import com.chatnote.directnotesui.directnoteslist.mapper.NotesToUiNotesMapper
import com.chatnote.directnotesui.mapper.UiNoteInteractionToSystemActionTypeMapper
import com.chatnote.directnotesui.model.UiActionableItem
import com.chatnote.directnotesui.model.UiNote
import com.chatnote.directnotesui.model.UiNoteActionableContent
import com.chatnote.directnotesui.model.UiNoteInteraction
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DirectNotesMapperModule {

    @Provides
    @Singleton
    fun provideFolderEntityToFolderMapper(dateFormatter: DateFormatter): Mapper<Note, UiNote> =
        NotesToUiNotesMapper(dateFormatter = dateFormatter)

    @Provides
    @Singleton
    fun provideActionTypeToSystemActionType(): Mapper<UiNoteInteraction, SystemActionType> =
        UiNoteInteractionToSystemActionTypeMapper()

    @Provides
    @Singleton
    fun provideActionTypeToUiActionTypeMapper(): Mapper<NoteActionType, UiNoteInteraction> =
        ActionTypeToUiActionTypeMapper()

    @Provides
    @Singleton
    fun provideActionableItemToUiActionableItemMapper(noteActionTypeToUiNoteInteractionMapper: Mapper<NoteActionType, UiNoteInteraction>): Mapper<NoteActionableItem, UiActionableItem> =
        ActionableItemToUiActionableItemMapper(noteActionTypeToUiNoteInteractionMapper = noteActionTypeToUiNoteInteractionMapper)

    @Provides
    @Singleton
    fun provideActionableContentToUiActionableContentMapper(actionTypeToUiActionTypeMapper: Mapper<NoteActionableItem, UiActionableItem>): Mapper<NoteActionableContent, UiNoteActionableContent> =
        ActionableContentToUiActionableContentMapper(actionTypeToUiActionTypeMapper = actionTypeToUiActionTypeMapper)

}
