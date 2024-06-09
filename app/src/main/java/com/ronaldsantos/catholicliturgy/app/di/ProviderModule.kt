package com.ronaldsantos.catholicliturgy.app.di

import android.content.Context
import com.ronaldsantos.catholicliturgy.library.framework.pref.CacheManager
import com.ronaldsantos.catholicliturgy.provider.language.AppLanguageProvider
import com.ronaldsantos.catholicliturgy.provider.language.LanguageProvider
import com.ronaldsantos.catholicliturgy.provider.resource.AppResourceProvider
import com.ronaldsantos.catholicliturgy.provider.resource.ResourceProvider
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
    fun providesThemeProvider(
        @ApplicationContext context: Context
    ): ThemeProvider {
        return AppThemeProvider(context)
    }

    @Provides
    @Singleton
    fun providesAppLanguageProvider(
        cacheManager: CacheManager
    ): LanguageProvider {
        return AppLanguageProvider(cacheManager)
    }

    @Provides
    @Singleton
    fun providesAppResourceProvider(
        @ApplicationContext context: Context
    ): ResourceProvider {
        return AppResourceProvider(context)
    }
}
