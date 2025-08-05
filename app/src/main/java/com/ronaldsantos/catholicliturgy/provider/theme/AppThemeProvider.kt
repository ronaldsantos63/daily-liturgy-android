package com.ronaldsantos.catholicliturgy.provider.theme

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.ui.geometry.Offset
import androidx.core.content.edit
import com.ronaldsantos.catholicliturgy.R
import com.ronaldsantos.catholicliturgy.app.theme.ThemeAnimationPhase
import com.ronaldsantos.catholicliturgy.library.framework.extension.getPrefs
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import timber.log.Timber

class AppThemeProvider(
    private val context: Context
) : ThemeProvider {
    private val sharedPreferences = context.getPrefs()

    private val defaultThemeValue = context.getString(R.string.pref_theme_default_value)

    private val preferenceKeyChangedFlow = MutableSharedFlow<String>(extraBufferCapacity = 1)
    private val themeAnimationPhaseFlow = MutableSharedFlow<ThemeAnimationPhase>(extraBufferCapacity = 1)
    private val buttonThemePositionFlow = MutableSharedFlow<Offset>(extraBufferCapacity = 1)

    private val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
        if (key != null) {
            preferenceKeyChangedFlow.tryEmit(key)
        }
    }

    private var currentTheme: ThemeProvider.Theme? = null
    private var _themeAnimationPhase: ThemeAnimationPhase = ThemeAnimationPhase.Idle
    private var _buttonThemePosition: Offset = Offset.Zero

    companion object {
        const val KEY_THEME = "pref_theme"
        const val TAG = "AppThemeProvider"
    }

    init {
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
    }

    override var theme: ThemeProvider.Theme
        get() {
            if (currentTheme != null) {
                Timber.tag(TAG).d("Returning cached theme: ${currentTheme!!.storageKey}")
                return currentTheme!!
            }
            Timber.tag(TAG).d("Getting theme from preferences")
            currentTheme = getThemeForStorageValue(sharedPreferences.getString(KEY_THEME, defaultThemeValue)!!)
            Timber.tag(TAG).d("Current theme: ${currentTheme!!.storageKey}")
            return currentTheme!!
        }
        set(value) {
            if (currentTheme == value) {
                Timber.tag(TAG).d("Theme is already set to: ${value.storageKey}, no change needed")
                return
            }
            Timber.tag(TAG).d("Setting theme to: ${value.storageKey}")
            currentTheme = value
            sharedPreferences.edit {
                putString(KEY_THEME, value.storageKey)
            }
        }

    override var themeAnimationPhase: ThemeAnimationPhase
        get() {
            Timber.tag(TAG).d("Getting theme animation phase: $_themeAnimationPhase")
            return _themeAnimationPhase
        }
        set(value) {
            Timber.tag(TAG).d("Setting theme animation phase to: $value")
            _themeAnimationPhase = value
            themeAnimationPhaseFlow.tryEmit(value)
        }

    override var buttonThemePosition: Offset
        get() {
            Timber.tag(TAG).d("Getting button theme position")
            return _buttonThemePosition
        }
        set(value) {
            Timber.tag(TAG).d("Setting button theme position to: $value")
            _buttonThemePosition = value
            buttonThemePositionFlow.tryEmit(value)
        }

    override fun observeThemeAnimationPhase(): Flow<ThemeAnimationPhase> {
        return themeAnimationPhaseFlow
            .distinctUntilChanged()
            .onStart {
                Timber.tag(TAG).d("Emitting initial theme animation phase: $_themeAnimationPhase")
                emit(_themeAnimationPhase)
            }
    }

    override fun observeButtonThemePosition(): Flow<Offset> {
        return buttonThemePositionFlow
            .distinctUntilChanged()
            .onStart {
                Timber.tag(TAG).d("Emitting initial button theme position: $_buttonThemePosition")
                emit(_buttonThemePosition)
            }
    }

    override fun observeTheme(): Flow<ThemeProvider.Theme> {
        return preferenceKeyChangedFlow
            .filter {
                Timber.tag(TAG).d("Filtering preference key: $it")
                it == KEY_THEME
            }
            .map {
                Timber.tag(TAG).d("Mapping preference key to theme")
                theme
            }
            .distinctUntilChanged()
            .onStart {
                val initialTheme = theme
                Timber.tag(TAG).d("Emitting initial theme value: ${initialTheme.storageKey}")
                emit(initialTheme)
            }
    }

    override fun isNightMode(): Boolean {
        Timber.tag(TAG).d("Checking if night mode is enabled")
        val isNightMode = when (theme) {
            ThemeProvider.Theme.LIGHT -> false
            ThemeProvider.Theme.DARK -> true
            ThemeProvider.Theme.SYSTEM -> isSystemInDarkTheme()
        }
        Timber.tag(TAG).d("Is night mode: $isNightMode")
        return isNightMode
    }

    override fun isSystemInDarkTheme(): Boolean {
        Timber.tag(TAG).d("Checking if system is in dark theme")
        val isSystemInDark = context.resources.configuration.uiMode and
                android.content.res.Configuration.UI_MODE_NIGHT_MASK ==
                android.content.res.Configuration.UI_MODE_NIGHT_YES
        Timber.tag(TAG).d("Is system in dark theme: $isSystemInDark")
        return isSystemInDark
    }

    private val ThemeProvider.Theme.storageKey: String
        get() {
            Timber.tag(TAG).d("Getting storage key for theme: $this")
            return when (this) {
                ThemeProvider.Theme.LIGHT -> context.getString(R.string.pref_theme_light_value)
                ThemeProvider.Theme.DARK -> context.getString(R.string.pref_theme_dark_value)
                ThemeProvider.Theme.SYSTEM -> context.getString(R.string.pref_theme_system_value)
            }
        }

    private fun getThemeForStorageValue(value: String): ThemeProvider.Theme {
        Timber.tag(TAG).d("Getting theme for storage value: $value")
        return when (value) {
            context.getString(R.string.pref_theme_light_value) -> ThemeProvider.Theme.LIGHT
            context.getString(R.string.pref_theme_dark_value) -> ThemeProvider.Theme.DARK
            context.getString(R.string.pref_theme_system_value) -> ThemeProvider.Theme.SYSTEM
            else -> {
                Timber.tag(TAG).w("Unknown theme value: $value, defaulting to SYSTEM")
                ThemeProvider.Theme.SYSTEM
            }
        }
    }

    override fun setNightMode(forceNight: Boolean) {
        Timber.tag(TAG).d("Setting night mode to: $forceNight")
        theme = if (forceNight) {
            ThemeProvider.Theme.DARK
        } else {
            ThemeProvider.Theme.LIGHT
        }
    }
}
