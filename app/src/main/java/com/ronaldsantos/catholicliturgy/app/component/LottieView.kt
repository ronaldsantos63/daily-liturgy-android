package com.ronaldsantos.catholicliturgy.app.component

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun LottieView(
    file: String,
    modifier: Modifier = Modifier,
    iterations: Int = 10
){
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.Asset(file))
    LottieAnimation(
        composition = composition,
        iterations = iterations,
        modifier = modifier.defaultMinSize(300.dp)
    )
}
