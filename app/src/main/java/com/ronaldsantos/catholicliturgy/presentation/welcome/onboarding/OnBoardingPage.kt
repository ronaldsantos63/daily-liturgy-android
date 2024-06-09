package com.ronaldsantos.catholicliturgy.presentation.welcome.onboarding

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.ronaldsantos.catholicliturgy.R

sealed class OnBoardingPage(
    @DrawableRes
    val image: Int,
    @StringRes val title: Int,
    @StringRes val description: Int
) {
    data object First : OnBoardingPage(
        image = R.drawable.intro_1,
        title = R.string.first_onboarding_title,
        description = R.string.first_onboarding_description
    )

    data object Second : OnBoardingPage(
        image = R.drawable.intro_2,
        title = R.string.second_onboarding_title,
        description = R.string.second_onboarding_description
    )

    data object Third : OnBoardingPage(
        image = R.drawable.intro_3,
        title = R.string.third_onboarding_title,
        description = R.string.third_onboarding_description
    )
}
