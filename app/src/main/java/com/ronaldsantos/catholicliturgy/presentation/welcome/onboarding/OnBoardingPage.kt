package com.ronaldsantos.catholicliturgy.presentation.welcome.onboarding

import androidx.annotation.DrawableRes
import com.ronaldsantos.catholicliturgy.R

sealed class OnBoardingPage(
    @DrawableRes
    val image: Int,
    val title: String,
    val description: String
) {
    data object First : OnBoardingPage(
        image = R.drawable.intro_1,
        title = "Leituras",
        description = "Tenha acesso as leituras diárias"
    )

    data object Second : OnBoardingPage(
        image = R.drawable.intro_2,
        title = "Salmos",
        description = "Além das leituras, você pode acessar os salmos"
    )

    data object Third : OnBoardingPage(
        image = R.drawable.intro_3,
        title = "Evangelho",
        description = "Tudo em um único lugar, incluindo o evangelho do dia"
    )
}
