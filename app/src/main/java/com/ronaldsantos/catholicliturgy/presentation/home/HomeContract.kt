package com.ronaldsantos.catholicliturgy.presentation.home

import com.ronaldsantos.catholicliturgy.domain.model.DailyLiturgyDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class HomeViewState(
    val dailyLiturgy: Flow<DailyLiturgyDto> = emptyFlow(),
)

sealed class HomeEvent {
    data object LoadDailyLiturgy : HomeEvent()
    data class AddOrRemoveFavorite(val dailyLiturgyDto: DailyLiturgyDto) : HomeEvent()
    data class DeleteFavorite(val id: Int) : HomeEvent()
}
