package com.ronaldsantos.catholicliturgy.app.di

import android.content.Context
import androidx.room.Room
import com.ronaldsantos.catholicliturgy.data.local.dao.DailyLiturgyDao
import com.ronaldsantos.catholicliturgy.data.local.db.DailyLiturgyDatabase
import com.ronaldsantos.catholicliturgy.domain.config.Configuration
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

private const val DB_NAME = "db_name"

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    @Named(value = DB_NAME)
    fun provideDatabaseName(config: Configuration): String {
        return config.databaseName()
    }

    @Provides
    @Singleton
    fun provideDatabase(
        @Named(value = DB_NAME) dbname: String,
        @ApplicationContext context: Context
    ): DailyLiturgyDatabase {
        return Room.databaseBuilder(context, DailyLiturgyDatabase::class.java, dbname)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideDailyLiturgyDao(appDatabase: DailyLiturgyDatabase): DailyLiturgyDao {
        return appDatabase.dailyLiturgyDao()
    }
}
