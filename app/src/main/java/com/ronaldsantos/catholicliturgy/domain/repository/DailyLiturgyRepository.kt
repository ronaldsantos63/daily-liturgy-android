package com.ronaldsantos.catholicliturgy.domain.repository

import com.ronaldsantos.catholicliturgy.data.model.local.DailyLiturgyEntity

interface DailyLiturgyRepository {
    suspend fun getDailyLiturgy(): DailyLiturgyEntity
    suspend fun getDailyLiturgyByDate(date: String): DailyLiturgyEntity
    suspend fun deleteDailyLiturgyByDate(date: String)
    suspend fun saveDailyLiturgy(dailyLiturgyEntity: DailyLiturgyEntity)
    suspend fun saveDailyLiturgyList(entityList: List<DailyLiturgyEntity>)
}
