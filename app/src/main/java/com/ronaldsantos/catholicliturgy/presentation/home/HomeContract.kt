package com.ronaldsantos.catholicliturgy.presentation.home

import com.ronaldsantos.catholicliturgy.domain.model.DailyLiturgyDto

data class HomeViewState(
    val dailyLiturgy: DailyLiturgyDto = DailyLiturgyDto(),
    val tabs: List<HomeTabs> = listOf(),
    val isRefreshing: Boolean = false,
)

sealed class HomeEvent {
    data class LoadDailyLiturgy(
        val isRefreshing: Boolean = false,
        val period: String? = null
    ) : HomeEvent()

    data object RetryLoadDailyLiturgy : HomeEvent()
    data object ToggleThemeMode : HomeEvent()
}
