package com.ronaldsantos.catholicliturgy.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.ronaldsantos.catholicliturgy.data.model.local.DailyLiturgyEntity
import com.ronaldsantos.catholicliturgy.library.framework.room.dao.BaseDao

@Dao
interface DailyLiturgyDao : BaseDao<DailyLiturgyEntity> {
    @Query("SELECT * FROM ${DailyLiturgyEntity.TABLE_NAME}")
    suspend fun getAllDailyLiturgy(): List<DailyLiturgyEntity>

    @Query("SELECT * FROM ${DailyLiturgyEntity.TABLE_NAME} WHERE id = :id")
    suspend fun getDailyLiturgyById(id: Int): DailyLiturgyEntity?

    @Query("SELECT * FROM ${DailyLiturgyEntity.TABLE_NAME} WHERE liturgy_date = :liturgyDate")
    suspend fun getDailyLiturgyDate(liturgyDate: String): DailyLiturgyEntity?

    @Query("DELETE FROM ${DailyLiturgyEntity.TABLE_NAME}")
    suspend fun deleteAllDailyLiturgy()

    @Query("DELETE FROM ${DailyLiturgyEntity.TABLE_NAME} WHERE liturgy_date = :date")
    suspend fun deleteLiturgyByDate(date: String)

    @Query("DELETE FROM ${DailyLiturgyEntity.TABLE_NAME} WHERE id = :id")
    suspend fun deleteLiturgyById(id: Int)
}
