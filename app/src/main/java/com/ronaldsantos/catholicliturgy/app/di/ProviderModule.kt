package com.ronaldsantos.catholicliturgy.app.di

import android.content.Context
import com.ronaldsantos.catholicliturgy.provider.theme.AppThemeProvider
import com.ronaldsantos.catholicliturgy.provider.theme.ThemeProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProviderModule {
    @Provides
    @Singleton
    fun providesThemeProvider(@ApplicationContext context: Context): ThemeProvider {
        return AppThemeProvider(context)
    }
}
