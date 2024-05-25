package com.ronaldsantos.catholicliturgy.presentation.welcome.navgraph

sealed class WelcomeScreen(val route: String) {
    data object OnBoarding : WelcomeScreen(route = "onBoarding_screen")
    data object Environment : WelcomeScreen(route = "environment_screen")
}
