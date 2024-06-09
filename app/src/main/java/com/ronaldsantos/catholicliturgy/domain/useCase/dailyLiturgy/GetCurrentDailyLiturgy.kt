package com.ronaldsantos.catholicliturgy.domain.useCase.dailyLiturgy

import android.util.Log
import androidx.annotation.VisibleForTesting
import com.ronaldsantos.catholicliturgy.domain.extension.asDto
import com.ronaldsantos.catholicliturgy.domain.extension.asEntity
import com.ronaldsantos.catholicliturgy.domain.model.DailyLiturgyDto
import com.ronaldsantos.catholicliturgy.domain.repository.DailyLiturgyRepository
import com.ronaldsantos.catholicliturgy.library.framework.usecase.LocalUseCase
import kotlinx.coroutines.flow.FlowCollector
import javax.inject.Inject

class GetCurrentDailyLiturgy @Inject constructor(
    @get:VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    internal val repository: DailyLiturgyRepository
) : LocalUseCase<GetCurrentDailyLiturgy.Params, DailyLiturgyDto>(){
    data class Params(
        val period: String
    )

    override suspend fun FlowCollector<DailyLiturgyDto>.execute(params: Params) {
        Log.d(TAG, "search by period in local: ${params.period}")
        val entity = repository.getDailyLiturgyLocalByDate(params.period)
        Log.d(TAG, "entity local: $entity")
        if (entity != null) {
            Log.d(TAG, "converting entity to dto")
            emit(entity.asDto)
            return
        }
        Log.d(TAG, "search by period in remote: ${params.period}")
        val response = repository.getDailyLiturgyByDate(params.period) ?: throw RuntimeException("DailyLiturgy not found")
        Log.d(TAG, "response: $response")
        val dto = response.asDto
        Log.d(TAG, "dto: $dto")
        repository.saveDailyLiturgy(dto.asEntity)
        Log.d(TAG, "converting response to dto")
        emit(response.asDto)
    }

    private companion object {
        const val TAG = "GetCurrentDailyLiturgy"
    }
}
