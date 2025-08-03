package com.ronaldsantos.catholicliturgy.provider.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.singleOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber

interface ThemeProvider {
    var theme: Theme
    fun observeTheme(): Flow<Theme>

    enum class Theme {
        LIGHT,
        DARK,
        SYSTEM
    }

    fun isNightMode(): Boolean

    fun setNightMode(forceNight: Boolean)
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
    val currentMode = isNightMode()
    if (currentMode != mode) {
        setNightMode(mode)
    }
    return mode
}
