package com.chatnote.directnotesui.di

import com.chatnote.coredomain.mapper.Mapper
import com.chatnote.coreui.model.SystemActionableItem
import com.chatnote.coreui.util.DateFormatter
import com.chatnote.directnotesdomain.model.ActionType
import com.chatnote.directnotesdomain.model.ActionableContent
import com.chatnote.directnotesdomain.model.ActionableItem
import com.chatnote.directnotesdomain.model.Note
import com.chatnote.directnotesui.actionablesheet.action.UiAction
import com.chatnote.directnotesui.directnoteslist.mapper.ActionTypeToUiActionTypeMapper
import com.chatnote.directnotesui.directnoteslist.mapper.ActionableContentToUiActionableContentMapper
import com.chatnote.directnotesui.directnoteslist.mapper.ActionableItemToUiActionableItemMapper
import com.chatnote.directnotesui.directnoteslist.mapper.NotesToUiNotesMapper
import com.chatnote.directnotesui.mapper.ActionTypeToSystemActionTypeMapper
import com.chatnote.directnotesui.model.UiActionType
import com.chatnote.directnotesui.model.UiActionableContent
import com.chatnote.directnotesui.model.UiActionableItem
import com.chatnote.directnotesui.model.UiNote
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
    fun provideActionTypeToSystemActionType(): Mapper<UiAction, SystemActionableItem> =
        ActionTypeToSystemActionTypeMapper()

    @Provides
    @Singleton
    fun provideActionTypeToUiActionTypeMapper(): Mapper<ActionType, UiActionType> =
        ActionTypeToUiActionTypeMapper()

    @Provides
    @Singleton
    fun provideActionableItemToUiActionableItemMapper(actionTypeToUiActionTypeMapper: Mapper<ActionType, UiActionType>): Mapper<ActionableItem, UiActionableItem> =
        ActionableItemToUiActionableItemMapper(actionTypeToUiActionTypeMapper = actionTypeToUiActionTypeMapper)

    @Provides
    @Singleton
    fun provideActionableContentToUiActionableContentMapper(actionTypeToUiActionTypeMapper: Mapper<ActionableItem, UiActionableItem>): Mapper<ActionableContent, UiActionableContent> =
        ActionableContentToUiActionableContentMapper(actionTypeToUiActionTypeMapper = actionTypeToUiActionTypeMapper)

}
