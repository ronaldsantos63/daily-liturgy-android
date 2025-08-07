package com.ronaldsantos.catholicliturgy.app.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


val Black = Color(0xFF000000)
val White = Color(0xFFFFFFFF)
val Red = Color(0xFFD13438)
val DividerLight = Color(0xFFE0E0E0)

val Purple500 = Color(0xFF7362E6)
val Purple100 = Color(0xFFE5E1FB)

val WhiteBG = Color(0xFFF2F1F6)
val SurfaceWhite = Color(0xFFFFFFFF)

val DarkText = Color(0xFF1D1D1F)
val GrayText = Color(0xFF9E9EA7)

val BackgroundDark = Color(0xFF121212)
val SurfaceDark = Color(0xFF1E1E1E)
val TextOnDark = Color(0xFFEFEFF0)
val DividerDark = Color(0xFF2A2A2A)

val navigationBackIconDark = White
val navigationBackIconLight = Black

val ColorScheme.navigationBackIconColor: Color
    @Composable get() = if (!isSystemInDarkTheme()) navigationBackIconLight else navigationBackIconDark

val ColorScheme.dividerColor: Color
    @Composable get() = if (!isSystemInDarkTheme()) DividerLight else DividerDark
