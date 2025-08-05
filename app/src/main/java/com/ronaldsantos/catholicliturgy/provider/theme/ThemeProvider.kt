package com.ronaldsantos.catholicliturgy.provider.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.geometry.Offset
import com.ronaldsantos.catholicliturgy.app.theme.ThemeAnimationPhase
import kotlinx.coroutines.flow.Flow
import timber.log.Timber

interface ThemeProvider {
    var theme: Theme
    var themeAnimationPhase: ThemeAnimationPhase
    var buttonThemePosition: Offset

    fun observeThemeAnimationPhase(): Flow<ThemeAnimationPhase>
    fun observeButtonThemePosition(): Flow<Offset>
    fun observeTheme(): Flow<Theme>

    enum class Theme {
        LIGHT,
        DARK,
        SYSTEM
    }

    fun isNightMode(): Boolean

    fun setNightMode(forceNight: Boolean)

    fun isSystemInDarkTheme(): Boolean
}

@Composable
fun ThemeProvider.shouldUseDarkMode(): Boolean {
    Timber.tag("ThemeProvider.shouldUseDarkMode").d("Checking if dark mode should be used")
    val themePreference = observeTheme().collectAsState(initial = theme)
    Timber.tag("ThemeProvider.shouldUseDarkMode").d("Current theme preference: ${themePreference.value}")
    val mode = when (themePreference.value) {
        ThemeProvider.Theme.LIGHT -> false
        ThemeProvider.Theme.DARK -> true
        else -> isSystemInDarkTheme()
    }
    return mode
}
