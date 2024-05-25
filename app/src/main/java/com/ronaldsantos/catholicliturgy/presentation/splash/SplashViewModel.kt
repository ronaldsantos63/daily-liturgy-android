package com.ronaldsantos.catholicliturgy.presentation.splash

import com.ronaldsantos.catholicliturgy.domain.useCase.welcome.ReadOnBoarding
import com.ronaldsantos.catholicliturgy.library.framework.base.MvvmViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val readOnBoarding: ReadOnBoarding
) : MvvmViewModel() {
    private val _startWelcome = MutableStateFlow(false)
    val startWelcome = _startWelcome.asStateFlow()

    init {
        readOnBoardingState()
    }

    private fun readOnBoardingState() = safeLaunch {
        call(readOnBoarding(Unit)) { completed ->
            _startWelcome.value = !completed
        }
    }
}
