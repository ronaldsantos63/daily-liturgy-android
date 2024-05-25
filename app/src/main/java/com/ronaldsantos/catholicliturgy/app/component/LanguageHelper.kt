package com.ronaldsantos.catholicliturgy.app.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import java.util.Locale

@Suppress("DEPRECATION")
@Composable
fun SetLanguage(languageCode: String) {
    val locale = Locale(languageCode)
    val configuration = LocalConfiguration.current
    configuration.setLocale(locale)
    val resources = LocalContext.current.resources
    resources.updateConfiguration(configuration, resources.displayMetrics)
}
