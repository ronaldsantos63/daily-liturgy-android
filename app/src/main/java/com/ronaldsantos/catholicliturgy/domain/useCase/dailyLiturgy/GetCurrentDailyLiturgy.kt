package com.ronaldsantos.catholicliturgy.domain.useCase.dailyLiturgy

import androidx.annotation.VisibleForTesting
import com.ronaldsantos.catholicliturgy.domain.extension.asDto
import com.ronaldsantos.catholicliturgy.domain.extension.asEntity
import com.ronaldsantos.catholicliturgy.domain.model.DailyLiturgyDto
import com.ronaldsantos.catholicliturgy.domain.repository.DailyLiturgyRepository
import com.ronaldsantos.catholicliturgy.library.framework.usecase.LocalUseCase
import kotlinx.coroutines.flow.FlowCollector
import timber.log.Timber
import javax.inject.Inject

class GetCurrentDailyLiturgy @Inject constructor(
    @get:VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    internal val repository: DailyLiturgyRepository
) : LocalUseCase<GetCurrentDailyLiturgy.Params, DailyLiturgyDto>(){
    data class Params(
        val period: String
    )

    override suspend fun FlowCollector<DailyLiturgyDto>.execute(params: Params) {
        Timber.tag(TAG).d("searching liturgy by period: ${params.period}")
        val entity = repository.getDailyLiturgyLocalByDate(params.period)
        if (entity != null) {
            emit(entity.asDto)
            return
        }
        val response = repository.getDailyLiturgyByDate(params.period) ?: throw RuntimeException("DailyLiturgy not found")
        val dto = response.asDto
        repository.saveDailyLiturgy(dto.asEntity)
        emit(response.asDto)
    }

    private companion object {
        const val TAG = "GetCurrentDailyLiturgy"
    }
}
