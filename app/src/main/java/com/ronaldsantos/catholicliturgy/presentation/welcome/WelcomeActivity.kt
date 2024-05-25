package com.ronaldsantos.catholicliturgy.presentation.welcome

import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ronaldsantos.catholicliturgy.app.component.SetupSystemUi
import com.ronaldsantos.catholicliturgy.app.theme.CatholicLiturgyColors
import com.ronaldsantos.catholicliturgy.app.theme.CatholicLiturgyTheme
import com.ronaldsantos.catholicliturgy.presentation.welcome.navgraph.WelcomeNavGraph

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val splashScreen = installSplashScreen()
            splashScreen.setKeepOnScreenCondition { true }
        }
        super.onCreate(savedInstanceState)
        setContent {
            WelcomeRoot()
        }
    }
}

@Composable
private fun WelcomeRoot() {
    CatholicLiturgyTheme {
        SetupSystemUi(rememberSystemUiController(), CatholicLiturgyColors.primary)
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = CatholicLiturgyColors.background
        ) {
            WelcomeNavGraph()
        }
    }
}
