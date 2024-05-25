package com.ronaldsantos.catholicliturgy.app.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

//val primaryLight = Color(0xFF13182F)
//val onPrimaryLight = Color(0xFFFFFFFF)
//val primaryContainerLight = Color(0xFF333750)
//val onPrimaryContainerLight = Color(0xFFC5C8E7)
//val secondaryLight = Color(0xFF5D5D69)
//val onSecondaryLight = Color(0xFFFFFFFF)
//val secondaryContainerLight = Color(0xFFE3E2F0)
//val onSecondaryContainerLight = Color(0xFF474853)
//val tertiaryLight = Color(0xFF281222)
//val onTertiaryLight = Color(0xFFFFFFFF)
//val tertiaryContainerLight = Color(0xFF4B3143)
//val onTertiaryContainerLight = Color(0xFFE5C0D7)
//val errorLight = Color(0xFFBA1A1A)
//val onErrorLight = Color(0xFFFFFFFF)
//val errorContainerLight = Color(0xFFFFDAD6)
//val onErrorContainerLight = Color(0xFF410002)
//val backgroundLight = Color(0xFFFCF8FB)
//val onBackgroundLight = Color(0xFF1C1B1D)
//val surfaceLight = Color(0xFFFCF8FB)
//val onSurfaceLight = Color(0xFF1C1B1D)
//val surfaceVariantLight = Color(0xFFE3E1EA)
//val onSurfaceVariantLight = Color(0xFF46464D)
//val outlineLight = Color(0xFF77767E)
//val outlineVariantLight = Color(0xFFC7C5CE)
//val scrimLight = Color(0xFF000000)
//val inverseSurfaceLight = Color(0xFF313032)
//val inverseOnSurfaceLight = Color(0xFFF3F0F2)
//val inversePrimaryLight = Color(0xFFC1C4E4)
//val surfaceDimLight = Color(0xFFDCD9DB)
//val surfaceBrightLight = Color(0xFFFCF8FB)
//val surfaceContainerLowestLight = Color(0xFFFFFFFF)
//val surfaceContainerLowLight = Color(0xFFF6F3F5)
//val surfaceContainerLight = Color(0xFFF0EDEF)
//val surfaceContainerHighLight = Color(0xFFEAE7EA)
//val surfaceContainerHighestLight = Color(0xFFE5E1E4)
//
//val primaryDark = Color(0xFFC1C4E4)
//val onPrimaryDark = Color(0xFF2B2F47)
//val primaryContainerDark = Color(0xFF1D2138)
//val onPrimaryContainerDark = Color(0xFFA8ABCA)
//val secondaryDark = Color(0xFFC6C5D3)
//val onSecondaryDark = Color(0xFF2F303A)
//val secondaryContainerDark = Color(0xFF41424D)
//val onSecondaryContainerDark = Color(0xFFD9D8E6)
//val tertiaryDark = Color(0xFFE1BCD3)
//val onTertiaryDark = Color(0xFF41293A)
//val tertiaryContainerDark = Color(0xFF321B2C)
//val onTertiaryContainerDark = Color(0xFFC7A4B9)
//val errorDark = Color(0xFFFFB4AB)
//val onErrorDark = Color(0xFF690005)
//val errorContainerDark = Color(0xFF93000A)
//val onErrorContainerDark = Color(0xFFFFDAD6)
//val backgroundDark = Color(0xFF131315)
//val onBackgroundDark = Color(0xFFE5E1E4)
//val surfaceDark = Color(0xFF131315)
//val onSurfaceDark = Color(0xFFE5E1E4)
//val surfaceVariantDark = Color(0xFF46464D)
//val onSurfaceVariantDark = Color(0xFFC7C5CE)
//val outlineDark = Color(0xFF919098)
//val outlineVariantDark = Color(0xFF46464D)
//val scrimDark = Color(0xFF000000)
//val inverseSurfaceDark = Color(0xFFE5E1E4)
//val inverseOnSurfaceDark = Color(0xFF313032)
//val inversePrimaryDark = Color(0xFF595D78)
//val surfaceDimDark = Color(0xFF131315)
//val surfaceBrightDark = Color(0xFF39393B)
//val surfaceContainerLowestDark = Color(0xFF0E0E10)
//val surfaceContainerLowDark = Color(0xFF1C1B1D)
//val surfaceContainerDark = Color(0xFF201F21)
//val surfaceContainerHighDark = Color(0xFF2A2A2C)
//val surfaceContainerHighestDark = Color(0xFF353436)


val Black = Color(0xFF000000)
val White = Color(0xFFFFFFFF)
val Transparent = Color(0x00000000)
val Blue = Color(0xFF252941)
val BlueDark = Color(0xFF05060B)
val Red = Color(0xFFD13438)
val RedDark = Color(0xFF982626)

val CardDark = Color(0xFF3B3E43)
val CardLight = White

val BackgroundLight = Color(0xFFF5F2F5)
val BackgroundDark = Color(0xFF24292E)

val DividerLight = Color(0xFFE0E0E0)
val DividerDark = Color(0xFF6E6E6E)

val GrayCircle = Color(0xFF919191)
val RedCircle = Color(0xFFD50000)
val GreenCircle = Color(0xFF00C853)
val BorderLine = Color(0xFFE5E5EA)

val Red700 = Color(0xFFD32F2F)

val Gray25 = Color(0xFFF8F8F8)
val Gray50 = Color(0xFFF1F1F1)
val Gray75 = Color(0xFFECECEC)
val Gray100 = Color(0xFFE1E1E1)
val Gray200 = Color(0xFFEEEEEE)
val Gray300 = Color(0xFFACACAC)
val Gray400 = Color(0xFF919191)
val Gray500 = Color(0xFF6E6E6E)
val Gray600 = Color(0xFF535353)
val Gray700 = Color(0xFF616161)
val Gray800 = Color(0xFF292929)
val Gray900 = Color(0xFF212121)
val Gray950 = Color(0xFF141414)

val selectedBottomItemColor = Red
val unselectedBottomItemColor = Gray500

val navigationBackIconDark = White
val navigationBackIconLight = Black

val ColorScheme.navigationBackIconColor: Color
    @Composable get() = if (!isSystemInDarkTheme()) navigationBackIconLight else navigationBackIconDark

val ColorScheme.dividerColor: Color
    @Composable get() = if (!isSystemInDarkTheme()) DividerLight else DividerDark

val ColorScheme.backgroundColor: Color
    @Composable get() = if (!isSystemInDarkTheme()) BackgroundLight else BackgroundDark

val ColorScheme.cardBackgroundColor: Color
    @Composable get() = if (!isSystemInDarkTheme()) CardLight else CardDark