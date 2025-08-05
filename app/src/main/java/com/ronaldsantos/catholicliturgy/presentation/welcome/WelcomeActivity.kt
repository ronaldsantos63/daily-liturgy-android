package com.ronaldsantos.catholicliturgy.presentation.welcome

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ronaldsantos.catholicliturgy.app.component.SetupSystemUi
import com.ronaldsantos.catholicliturgy.app.theme.CatholicLiturgyColors
import com.ronaldsantos.catholicliturgy.app.theme.CatholicLiturgyTheme
import com.ronaldsantos.catholicliturgy.presentation.welcome.navgraph.WelcomeNavGraph
import com.ronaldsantos.catholicliturgy.provider.language.LanguageProvider
import com.ronaldsantos.catholicliturgy.provider.theme.ThemeProvider
import com.ronaldsantos.catholicliturgy.provider.theme.shouldUseDarkMode
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WelcomeActivity : FragmentActivity() {

    @Inject
    lateinit var languageProvider: LanguageProvider

    @Inject
    lateinit var themeProvider: ThemeProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            languageProvider.setLocale(languageProvider.getLanguageCode(), LocalContext.current)
            WelcomeRoot(themeProvider)
        }
    }
}

@Composable
private fun WelcomeRoot(themeProvider: ThemeProvider) {
    val isDarkMode = themeProvider.shouldUseDarkMode()
    CatholicLiturgyTheme(darkTheme = isDarkMode) {
        SetupSystemUi(rememberSystemUiController(), CatholicLiturgyColors.primary)
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = CatholicLiturgyColors.background
        ) {
            WelcomeNavGraph()
        }
    }
}
