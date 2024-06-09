package com.ronaldsantos.catholicliturgy.domain.repository

import com.ronaldsantos.catholicliturgy.data.model.local.DailyLiturgyEntity
import com.ronaldsantos.catholicliturgy.data.model.remote.response.DailyLiturgyResponse

interface DailyLiturgyRepository {
    suspend fun getDailyLiturgy(): DailyLiturgyResponse?
    suspend fun getDailyLiturgyByDate(date: String): DailyLiturgyResponse?
    suspend fun getDailyLiturgyLocalByDate(date: String): DailyLiturgyEntity?
    suspend fun deleteDailyLiturgyById(id: Int)
    suspend fun saveDailyLiturgy(dailyLiturgyEntity: DailyLiturgyEntity)
    suspend fun saveDailyLiturgyList(entityList: List<DailyLiturgyEntity>)
}
