package com.ronaldsantos.catholicliturgy.presentation.main

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import com.ronaldsantos.catholicliturgy.app.component.SetupSystemUi
import com.ronaldsantos.catholicliturgy.app.theme.CatholicLiturgyColors
import com.ronaldsantos.catholicliturgy.app.theme.CatholicLiturgyTheme
import com.ronaldsantos.catholicliturgy.presentation.home.NavGraphs
import com.ronaldsantos.catholicliturgy.provider.navigation.AppNavigationProvider
import com.ronaldsantos.catholicliturgy.provider.theme.shouldUseDarkMode

@Composable
fun MainRoot(viewModel: MainViewModel = hiltViewModel(), finish: () -> Unit) {
    val navController = rememberNavController()

    val isDarkMode = viewModel.themeProvider().shouldUseDarkMode()

    val currentBackStackEntryAsState by navController.currentBackStackEntryAsState()
    val destination = currentBackStackEntryAsState?.destination?.route
        ?: NavGraphs.root.startRoute.route

    if (destination == NavGraphs.root.startRoute.route) {
        BackHandler { finish() }
    }

    CatholicLiturgyTheme(darkTheme = isDarkMode) {
        SetupSystemUi(rememberSystemUiController(), CatholicLiturgyColors.primary)

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = CatholicLiturgyColors.background
        ) {
            DestinationsNavHost(
                navController = navController,
                navGraph = NavGraphs.root,
                dependenciesContainerBuilder = {
                    dependency(AppNavigationProvider(navController))
                }
            )
        }
    }
}
