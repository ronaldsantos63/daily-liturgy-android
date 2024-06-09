package com.ronaldsantos.catholicliturgy.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.ronaldsantos.catholicliturgy.domain.useCase.dailyLiturgy.GetCurrentDailyLiturgy
import com.ronaldsantos.catholicliturgy.library.framework.base.BaseViewState
import com.ronaldsantos.catholicliturgy.library.framework.base.MviViewModel
import com.ronaldsantos.catholicliturgy.library.framework.extension.asDateStringBrl
import com.ronaldsantos.catholicliturgy.provider.theme.ThemeProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCurrentDailyLiturgy: GetCurrentDailyLiturgy,
    private val themeProvider: ThemeProvider,
) : MviViewModel<BaseViewState<HomeViewState>, HomeEvent>() {
    private var lastPeriodSearched by mutableStateOf("")
    val isDarkMode: StateFlow<Boolean>
        get() = themeProvider
            .observeTheme()
            .map { theme ->
                theme == ThemeProvider.Theme.DARK
            }
            .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    override fun onTriggerEvent(eventType: HomeEvent) {
        when (eventType) {
            is HomeEvent.LoadDailyLiturgy -> onLoadDailyLiturgy(
                eventType.isRefreshing,
                eventType.period
            )

            is HomeEvent.RetryLoadDailyLiturgy -> onLoadDailyLiturgy(
                isRefreshing = false,
                period = lastPeriodSearched
            )

            is HomeEvent.ThemeMode -> onChangeThemeMode(eventType.isDarkMode)
        }
    }

    private fun onLoadDailyLiturgy(isRefreshing: Boolean, period: String? = null) = safeLaunch {
        lastPeriodSearched = period.orEmpty()

        if (isRefreshing) {
            setState(BaseViewState.Data(HomeViewState(isRefreshing = true)))
        } else {
            setState(BaseViewState.Loading)
        }

        val date = Calendar.getInstance().time

        getCurrentDailyLiturgy.invoke(
            GetCurrentDailyLiturgy.Params(period = period ?: date.asDateStringBrl)
        ).onEach { dailyLiturgyDto ->
            val tabs = dailyLiturgyDto.readings.map {
                HomeTabs.parseType(it.type)
            }
            setState(
                BaseViewState.Data(
                    HomeViewState(
                        dailyLiturgy = dailyLiturgyDto,
                        tabs = tabs,
                        isRefreshing = false,
                    )
                )
            )
        }.catch {
            Timber.tag(TAG).e(it, "Error loading daily liturgy")
            setState(BaseViewState.Error(it))
        }.launchIn(viewModelScope)
    }

    private fun onChangeThemeMode(isDarkMode: Boolean) {
        themeProvider.theme = if (isDarkMode) {
            ThemeProvider.Theme.DARK
        } else {
            ThemeProvider.Theme.LIGHT
        }
    }

    companion object {
        private const val TAG = "HomeViewModel"
    }
}
