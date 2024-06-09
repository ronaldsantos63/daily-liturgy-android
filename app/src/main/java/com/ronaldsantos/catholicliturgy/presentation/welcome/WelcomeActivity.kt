package com.ronaldsantos.catholicliturgy.presentation.welcome

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.fragment.app.FragmentActivity
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ronaldsantos.catholicliturgy.app.component.SetupSystemUi
import com.ronaldsantos.catholicliturgy.app.theme.CatholicLiturgyColors
import com.ronaldsantos.catholicliturgy.app.theme.CatholicLiturgyTheme
import com.ronaldsantos.catholicliturgy.presentation.welcome.navgraph.WelcomeNavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
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
