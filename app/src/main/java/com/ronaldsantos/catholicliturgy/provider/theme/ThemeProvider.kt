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
//    val themePreference = observeTheme().collectAsState(initial = ThemeProvider.Theme.SYSTEM)
    val themePreference = observeTheme().collectAsState(initial = ThemeProvider.Theme.SYSTEM)
    val mode = when (themePreference.value) {
        ThemeProvider.Theme.LIGHT -> false
        ThemeProvider.Theme.DARK -> true
        else -> isSystemInDarkTheme()
    }
    setNightMode(mode)
    return mode
}
