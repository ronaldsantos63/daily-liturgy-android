package com.ronaldsantos.catholicliturgy.app.di

import android.content.Context
import com.ronaldsantos.catholicliturgy.data.local.dao.DailyLiturgyDao
import com.ronaldsantos.catholicliturgy.data.remote.CatholicLiturgyApi
import com.ronaldsantos.catholicliturgy.data.repository.DailyLiturgyRepositoryImpl
import com.ronaldsantos.catholicliturgy.data.repository.WelcomeRepositoryImpl
import com.ronaldsantos.catholicliturgy.domain.repository.DailyLiturgyRepository
import com.ronaldsantos.catholicliturgy.domain.repository.WelcomeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun providesWelcomeRepository(@ApplicationContext context: Context): WelcomeRepository {
        return WelcomeRepositoryImpl(context)
    }

    @Provides
    @Singleton
    fun providesDailyLiturgyRepository(
        service: CatholicLiturgyApi,
        dao: DailyLiturgyDao,
    ): DailyLiturgyRepository {
        return DailyLiturgyRepositoryImpl(service, dao)
    }
}
