package com.chatnote.coredata.di.di

import com.chatnote.coredata.di.mapper.NoteEntityExtraTypeToNoteExtraTypeMapper
import com.chatnote.coredata.di.mapper.NoteExtraTypeToNoteEntityExtraTypeMapper
import com.chatnote.coredata.di.model.NoteEntityExtraType
import com.chatnote.coredomain.mapper.Mapper
import com.chatnote.coredomain.models.NoteExtraType
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NoteMapperModule {

    @Provides
    @Singleton
    fun provideNoteExtraTypeToNoteEntityExtraTypeMapper(): Mapper<NoteExtraType, NoteEntityExtraType> =
        NoteExtraTypeToNoteEntityExtraTypeMapper()

    @Provides
    @Singleton
    fun provideNoteEntityExtraTypeToNoteExtraTypeMapper(): Mapper<NoteEntityExtraType, NoteExtraType> =
        NoteEntityExtraTypeToNoteExtraTypeMapper()
}