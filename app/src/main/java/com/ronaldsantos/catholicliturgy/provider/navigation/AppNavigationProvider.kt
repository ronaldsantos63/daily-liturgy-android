package com.ronaldsantos.catholicliturgy.provider.navigation

import androidx.navigation.NavController

class AppNavigationProvider(
    private val navController: NavController
) : NavigationProvider {
    override fun navigateUp() {
        navController.navigateUp()
    }
}
