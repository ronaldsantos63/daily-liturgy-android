package com.ronaldsantos.catholicliturgy.data.repository

import androidx.annotation.VisibleForTesting
import com.ronaldsantos.catholicliturgy.data.local.dao.DailyLiturgyDao
import com.ronaldsantos.catholicliturgy.data.model.local.DailyLiturgyEntity
import com.ronaldsantos.catholicliturgy.data.model.remote.response.DailyLiturgyResponse
import com.ronaldsantos.catholicliturgy.data.remote.CatholicLiturgyApi
import com.ronaldsantos.catholicliturgy.domain.repository.DailyLiturgyRepository
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DailyLiturgyRepositoryImpl(
    @get:VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    val service: CatholicLiturgyApi,
    @get:VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    val dao: DailyLiturgyDao
) : DailyLiturgyRepository {
    override suspend fun getDailyLiturgy(): DailyLiturgyResponse? {
        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) //or use getDateInstance()
        val formattedDate = formatter.format(date)

        return service.fetchDalyLiturgy(period = formattedDate)

//        val entity = DailyLiturgyEntity(
//            id = 0,
//            entryTitle = response.entryTitle,
//            liturgyDate = response.date,
//            color = response.color,
//            created = formattedDate,
//            readings = ReadingsEntity(
//                firstReading = response.readings.firstReading,
//                psalm = response.readings.psalm,
//                gospel = response.readings.gospel,
//                secondReading = response.readings.secondReading
//            )
//        )
//
//        dao.insert(entity)
//        return entity
    }

    override suspend fun getDailyLiturgyByDate(date: String): DailyLiturgyResponse? {
        return service.fetchDalyLiturgy(period = date)
    }

    override suspend fun getDailyLiturgyLocalByDate(date: String): DailyLiturgyEntity? {
        return dao.getDailyLiturgyDate(date)
    }

    override suspend fun deleteDailyLiturgyById(id: Int) {
        dao.deleteLiturgyById(id)
    }

    override suspend fun saveDailyLiturgy(dailyLiturgyEntity: DailyLiturgyEntity) {
        return dao.insert(dailyLiturgyEntity)
    }

    override suspend fun saveDailyLiturgyList(entityList: List<DailyLiturgyEntity>) {
        dao.insert(entityList)
    }
}
