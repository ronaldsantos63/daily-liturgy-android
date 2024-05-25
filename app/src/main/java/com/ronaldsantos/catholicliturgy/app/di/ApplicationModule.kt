package com.ronaldsantos.catholicliturgy.app.di

import android.content.Context
import com.ronaldsantos.catholicliturgy.app.CatholicLiturgyApplication
import com.ronaldsantos.catholicliturgy.data.config.ConfigurationImpl
import com.ronaldsantos.catholicliturgy.domain.config.Configuration
import com.ronaldsantos.catholicliturgy.library.framework.app.AppInitializer
import com.ronaldsantos.catholicliturgy.library.framework.app.AppInitializerImpl
import com.ronaldsantos.catholicliturgy.library.framework.initializer.timber.TimberInitializer
import com.ronaldsantos.catholicliturgy.library.framework.pref.CacheManager
import com.ronaldsantos.catholicliturgy.library.framework.pref.CacheManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {
    @Provides
    @Singleton
    fun providesApplication(): CatholicLiturgyApplication {
        return CatholicLiturgyApplication()
    }

    @Provides
    @Singleton
    fun providesCacheManager(@ApplicationContext context: Context): CacheManager {
        return CacheManagerImpl(context)
    }

    @Provides
    @Singleton
    fun provideConfiguration(
        @ApplicationContext context: Context
    ): Configuration = ConfigurationImpl(context)

    @Provides
    @Singleton
    fun providesAppInitializer(
        timberInitializer: TimberInitializer
    ): AppInitializer {
        return AppInitializerImpl(timberInitializer)
    }

}