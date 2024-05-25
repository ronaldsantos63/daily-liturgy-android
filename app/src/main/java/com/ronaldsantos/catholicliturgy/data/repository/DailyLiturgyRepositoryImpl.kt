package com.ronaldsantos.catholicliturgy.data.repository

import androidx.annotation.VisibleForTesting
import com.ronaldsantos.catholicliturgy.data.local.dao.DailyLiturgyDao
import com.ronaldsantos.catholicliturgy.data.model.local.DailyLiturgyEntity
import com.ronaldsantos.catholicliturgy.data.model.local.ReadingsEntity
import com.ronaldsantos.catholicliturgy.data.model.remote.request.DailyLiturgyRequest
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
    override suspend fun getDailyLiturgy(): DailyLiturgyEntity {
        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) //or use getDateInstance()
        val formattedDate = formatter.format(date)

        val entityFound = dao.getDailyLiturgyDate(formattedDate)
        if (entityFound != null) {
            return entityFound
        }

        val response = service.fetchDalyLiturgy(DailyLiturgyRequest(period = formattedDate))

        val entity = DailyLiturgyEntity(
            id = 0,
            entryTitle = response.entryTitle,
            liturgyDate = response.date,
            color = response.color,
            created = formattedDate,
            readings = ReadingsEntity(
                firstReading = response.readings.firstReading,
                psalm = response.readings.psalm,
                gospel = response.readings.gospel,
                secondReading = response.readings.secondReading
            )
        )

        dao.insert(entity)
        return entity
    }

    override suspend fun getDailyLiturgyByDate(date: String): DailyLiturgyEntity {
        TODO("Not yet implemented")
    }

    override suspend fun deleteDailyLiturgyByDate(date: String) {
        TODO("Not yet implemented")
    }

    override suspend fun saveDailyLiturgy(dailyLiturgyEntity: DailyLiturgyEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun saveDailyLiturgyList(entityList: List<DailyLiturgyEntity>) {
        TODO("Not yet implemented")
    }

}
