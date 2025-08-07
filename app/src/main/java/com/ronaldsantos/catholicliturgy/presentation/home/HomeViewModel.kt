package com.ronaldsantos.catholicliturgy.presentation.home

import android.content.Context
import android.speech.tts.TextToSpeech
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.viewModelScope
import com.ronaldsantos.catholicliturgy.app.theme.ThemeAnimationPhase
import com.ronaldsantos.catholicliturgy.domain.useCase.dailyLiturgy.GetCurrentDailyLiturgy
import com.ronaldsantos.catholicliturgy.library.framework.base.BaseViewState
import com.ronaldsantos.catholicliturgy.library.framework.base.MviViewModel
import com.ronaldsantos.catholicliturgy.library.framework.extension.asDateStringBR
import com.ronaldsantos.catholicliturgy.provider.theme.ThemeProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCurrentDailyLiturgy: GetCurrentDailyLiturgy,
    private val themeProvider: ThemeProvider,
    @ApplicationContext private val appContext: Context,
) : MviViewModel<BaseViewState<HomeViewState>, HomeEvent>() {
    private var textToSpeech: TextToSpeech? = null
    private var lastPeriodSearched by mutableStateOf("")

    val isDarkMode: StateFlow<Boolean>
        get() = themeProvider
            .observeTheme()
            .map { theme ->
                Timber.tag(TAG).d("Checking dark mode for theme: $theme")
                when (theme) {
                    ThemeProvider.Theme.LIGHT -> false
                    ThemeProvider.Theme.DARK -> true
                    ThemeProvider.Theme.SYSTEM -> themeProvider.isSystemInDarkTheme()
                }
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
            is HomeEvent.ToggleThemeMode -> {
                if (themeProvider.themeAnimationPhase != ThemeAnimationPhase.Idle) {
                    Timber.tag(TAG).w("Theme animation is not idle, skipping toggle")
                    return
                }
                onChangeThemeMode()
            }

            is HomeEvent.ReadAloud -> {
                Timber.tag(TAG).d("Read Aloud event triggered")
                if (textToSpeech == null) {
                    Timber.tag(TAG).d("Initializing TextToSpeech")
                    textToSpeech = TextToSpeech(appContext) { status ->
                        if (status == TextToSpeech.SUCCESS) {
                            Timber.tag(TAG).d("TextToSpeech initialized successfully")
                            textToSpeech?.language = Locale("pt", "BR")
                            speakText(eventType.viewState)
                        } else {
                            Timber.tag(TAG).e("Failed to initialize TextToSpeech")
                        }
                    }
                } else {
                    Timber.tag(TAG).d("TextToSpeech already initialized")
                    speakText(eventType.viewState)
                }
            }
        }
    }

    fun updateButtonPosition(position: Offset) {
        Timber.tag(TAG).d("Updating button position to: $position")
        themeProvider.buttonThemePosition = position
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
            GetCurrentDailyLiturgy.Params(period = period ?: date.asDateStringBR)
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
        }.catch { exception ->
            Timber.tag(TAG).e(exception, "Error loading daily liturgy")
            handleError(exception)
        }.launchIn(viewModelScope)
    }

    private fun onChangeThemeMode() {
        themeProvider.themeAnimationPhase = ThemeAnimationPhase.Expanding
    }

    private fun speakText(viewState: HomeViewState) {
        val text = viewState
            .dailyLiturgy
            .readings
            .joinToString(separator = "\n") { android.text.Html.fromHtml(it.text, android.text.Html.FROM_HTML_MODE_LEGACY).toString() }

        textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "liturgia-id")
    }

    companion object {
        private const val TAG = "HomeViewModel"
    }
}
