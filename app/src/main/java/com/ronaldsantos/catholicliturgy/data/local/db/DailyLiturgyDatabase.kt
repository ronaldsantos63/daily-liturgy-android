package com.ronaldsantos.catholicliturgy.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ronaldsantos.catholicliturgy.data.local.converter.ReadingsEntityListConverter
import com.ronaldsantos.catholicliturgy.data.local.dao.DailyLiturgyDao
import com.ronaldsantos.catholicliturgy.data.model.local.DailyLiturgyEntity

@Database(
    entities = [DailyLiturgyEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(ReadingsEntityListConverter::class)
abstract class DailyLiturgyDatabase : RoomDatabase() {
    abstract fun dailyLiturgyDao(): DailyLiturgyDao
}
