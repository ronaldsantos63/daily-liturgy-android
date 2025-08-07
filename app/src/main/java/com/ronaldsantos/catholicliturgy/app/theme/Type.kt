package com.ronaldsantos.catholicliturgy.app.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val TheOneTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = Baloo2Fonts,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp
    ),
    titleLarge = TextStyle(
        fontFamily = Baloo2Fonts,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = Baloo2Fonts,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    labelLarge = TextStyle(
        fontFamily = Baloo2Fonts,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp
    )
)
