package com.ronaldsantos.catholicliturgy.presentation.main

import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ronaldsantos.catholicliturgy.app.theme.ThemeAnimationPhase
import com.ronaldsantos.catholicliturgy.provider.theme.ThemeProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val themeProvider: ThemeProvider) : ViewModel() {
    val buttonPosition: StateFlow<Offset>
        get() = themeProvider.observeButtonThemePosition().stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = Offset.Zero
        )

    val themeAnimationPhase: StateFlow<ThemeAnimationPhase> =
        themeProvider.observeThemeAnimationPhase()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = ThemeAnimationPhase.Idle
            )

    fun themeProvider() = themeProvider

    fun updateThemeAnimationPhase(phase: ThemeAnimationPhase) {
        Timber.tag(TAG).d("Updating theme animation phase to: $phase")
        themeProvider.themeAnimationPhase = phase
    }

    fun toggleTheme() {
        Timber.tag(TAG).d("Toggling theme")
        val currentTheme = themeProvider.theme
        val isDarkMode = when (currentTheme) {
            ThemeProvider.Theme.LIGHT -> false
            ThemeProvider.Theme.DARK -> true
            ThemeProvider.Theme.SYSTEM -> themeProvider.isSystemInDarkTheme()
        }
        val newTheme = if (isDarkMode) {
            ThemeProvider.Theme.LIGHT
        } else {
            ThemeProvider.Theme.DARK
        }
        themeProvider.theme = newTheme
        Timber.tag(TAG).d("Theme changed to: $newTheme")
    }

    private companion object {
        val TAG: String = MainViewModel::class.java.simpleName
    }
}
