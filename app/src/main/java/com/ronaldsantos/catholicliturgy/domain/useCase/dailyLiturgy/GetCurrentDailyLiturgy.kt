package com.ronaldsantos.catholicliturgy.domain.useCase.dailyLiturgy

import androidx.annotation.VisibleForTesting
import com.ronaldsantos.catholicliturgy.domain.model.DailyLiturgyDto
import com.ronaldsantos.catholicliturgy.domain.model.ReadingsDto
import com.ronaldsantos.catholicliturgy.domain.repository.DailyLiturgyRepository
import com.ronaldsantos.catholicliturgy.library.framework.usecase.ReturnUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCurrentDailyLiturgy @Inject constructor(
    @get:VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    internal val repository: DailyLiturgyRepository
) : ReturnUseCase<Unit, DailyLiturgyDto>(){
//    data class Params(
//        val liturgyDate: String,
//    )

    override suspend fun execute(params: Unit): Flow<DailyLiturgyDto> = flow {
        val response = repository.getDailyLiturgy()
        val dto = DailyLiturgyDto(
            id = response.id,
            entryTitle = response.entryTitle,
            liturgyDate = response.liturgyDate,
            color = response.color,
            readings = ReadingsDto(
                firstReading = response.readings.firstReading,
                psalm = response.readings.psalm,
                secondReading = response.readings.secondReading,
                gospel = response.readings.gospel
            ),
            created = response.created
        )
        emit(dto)
    }
}
