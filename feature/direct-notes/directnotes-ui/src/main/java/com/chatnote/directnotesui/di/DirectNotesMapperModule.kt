package com.chatnote.directnotesui.di

import com.chatnote.coredomain.mapper.Mapper
import com.chatnote.coreui.model.SystemActionType
import com.chatnote.coreui.util.DateFormatter
import com.chatnote.directnotesdomain.model.ActionType
import com.chatnote.directnotesdomain.model.ActionableItem
import com.chatnote.directnotesdomain.model.Note
import com.chatnote.directnotesdomain.model.NoteActionableContent
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
    fun provideActionTypeToUiActionTypeMapper(): Mapper<ActionType, UiNoteInteraction> =
        ActionTypeToUiActionTypeMapper()

    @Provides
    @Singleton
    fun provideActionableItemToUiActionableItemMapper(actionTypeToUiNoteInteractionMapper: Mapper<ActionType, UiNoteInteraction>): Mapper<ActionableItem, UiActionableItem> =
        ActionableItemToUiActionableItemMapper(actionTypeToUiNoteInteractionMapper = actionTypeToUiNoteInteractionMapper)

    @Provides
    @Singleton
    fun provideActionableContentToUiActionableContentMapper(actionTypeToUiActionTypeMapper: Mapper<ActionableItem, UiActionableItem>): Mapper<NoteActionableContent, UiNoteActionableContent> =
        ActionableContentToUiActionableContentMapper(actionTypeToUiActionTypeMapper = actionTypeToUiActionTypeMapper)

}
