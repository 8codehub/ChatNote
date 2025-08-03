package com.chatnote.coreui.di

import android.content.Context
import com.chatnote.coreui.util.DateFormatter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.Locale
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DateFormatterModule {

    @Provides
    @Singleton
    fun provideResultErrorToErrorMessageIdMapper(locale: Locale): DateFormatter {
        return DateFormatter(locale = locale)
    }

    @Provides
    fun provideCurrentLocale(@ApplicationContext context: Context): Locale {
        return try {
            context.resources.configuration.locales[0]
        } catch (th: Throwable) {
            Locale.getDefault()
        }
    }
}