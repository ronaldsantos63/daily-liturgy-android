package com.ronaldsantos.catholicliturgy.presentation.main

import androidx.lifecycle.ViewModel
import com.ronaldsantos.catholicliturgy.provider.theme.ThemeProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val themeProvider: ThemeProvider) : ViewModel() {

    fun themeProvider() = themeProvider
}
