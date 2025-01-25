package com.sendme.data.di

import android.content.Context
import androidx.room.Room
import com.sendme.common.di.IoDispatcher
import com.sendme.data.db.FolderDao
import com.sendme.data.db.HomeListDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.asExecutor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeListDatabaseModule {

    @Provides
    @Singleton
    fun provideHomeListDatabase(
        @ApplicationContext context: Context,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): HomeListDatabase {
        return Room.databaseBuilder(
            context,
            HomeListDatabase::class.java,
            "home_list_database"
        )
            .setQueryExecutor(dispatcher.asExecutor())
            .setTransactionExecutor(dispatcher.asExecutor())
            .build()
    }

    @Provides
    fun provideFolderDao(database: HomeListDatabase): FolderDao = database.folderDao()
}
