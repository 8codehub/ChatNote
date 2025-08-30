package com.chatnote.data.di

import com.chatnote.coredata.di.db.NoteWithExtras
import com.chatnote.coredata.di.model.NoteEntity
import com.chatnote.coredata.di.model.NoteEntityExtraType
import com.chatnote.coredata.di.model.NoteExtraEntity
import com.chatnote.coredomain.mapper.Mapper
import com.chatnote.coredomain.models.NoteExtra
import com.chatnote.coredomain.models.NoteExtraType
import com.chatnote.data.mapper.NoteEntityToNoteMapper
import com.chatnote.data.mapper.NoteExtraEntityToNoteExtraMapper
import com.chatnote.data.mapper.NoteExtraToNoteExtraEntityMapper
import com.chatnote.data.mapper.NoteToNoteEntityMapper
import com.chatnote.data.mapper.NoteWithExtrasToNoteMapper
import com.chatnote.directnotesdomain.model.Note
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
    fun provideNoteEntityToNoteMapper(): Mapper<NoteEntity, Note> =
        NoteEntityToNoteMapper()

    @Provides
    @Singleton
    fun provideNoteToNoteEntityMapper(): Mapper<Note, NoteEntity> =
        NoteToNoteEntityMapper()

    @Provides
    @Singleton
    fun provideNoteExtraEntityToNoteMapper(): Mapper<NoteExtraEntity, NoteExtra> =
        NoteExtraEntityToNoteExtraMapper()


    @Provides
    @Singleton
    fun provideNoteExtraToNoteExtraEntityMapper(mapper: Mapper<NoteExtraType, NoteEntityExtraType>): Mapper<NoteExtra, NoteExtraEntity> =
        NoteExtraToNoteExtraEntityMapper(mapper = mapper)

    @Provides
    @Singleton
    fun provideNoteWithExtrasToNoteMapper(extrasMapper: Mapper<NoteExtraEntity, NoteExtra>): Mapper<NoteWithExtras, Note> =
        NoteWithExtrasToNoteMapper(extrasMapper)
}
