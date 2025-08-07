package com.ronaldsantos.catholicliturgy.presentation.main

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import com.ronaldsantos.catholicliturgy.app.component.SetupSystemUi
import com.ronaldsantos.catholicliturgy.app.theme.BackgroundDark
import com.ronaldsantos.catholicliturgy.app.theme.CatholicLiturgyColors
import com.ronaldsantos.catholicliturgy.app.theme.CatholicLiturgyTheme
import com.ronaldsantos.catholicliturgy.app.theme.ThemeAnimationPhase
import com.ronaldsantos.catholicliturgy.app.theme.WhiteBG
import com.ronaldsantos.catholicliturgy.presentation.home.NavGraphs
import com.ronaldsantos.catholicliturgy.provider.navigation.AppNavigationProvider
import com.ronaldsantos.catholicliturgy.provider.theme.shouldUseDarkMode
import kotlinx.coroutines.delay
import timber.log.Timber
import kotlin.math.hypot

@Composable
fun MainRoot(viewModel: MainViewModel = hiltViewModel(), finish: () -> Unit) {
    val navController = rememberNavController()

    val isDarkMode = viewModel.themeProvider().shouldUseDarkMode()
    val themeButtonPosition by viewModel.buttonPosition.collectAsState()

    val currentBackStackEntryAsState by navController.currentBackStackEntryAsState()
    val destination = currentBackStackEntryAsState?.destination?.route
        ?: NavGraphs.root.startRoute.route

    if (destination == NavGraphs.root.startRoute.route) {
        BackHandler { finish() }
    }

    // Estado da animação
    val animationPhase by viewModel.themeAnimationPhase.collectAsState()

    // Cálculo do raio máximo para cobrir toda a tela
    val maxRadius = with(LocalDensity.current) {
        val screenWidth = LocalConfiguration.current.screenWidthDp.dp.toPx()
        val screenHeight = LocalConfiguration.current.screenHeightDp.dp.toPx()
        hypot(screenWidth, screenHeight)
    }

    // Raio animado com interpolação suave
    val animatedRadius by animateFloatAsState(
        targetValue = when (animationPhase) {
            ThemeAnimationPhase.Expanding -> maxRadius
            ThemeAnimationPhase.Collapsing -> 0f
            else -> 0f
        },
        animationSpec = tween(
            durationMillis = 600,
            easing = FastOutSlowInEasing
        ),
        label = "radius"
    )

    // Cor do fundo com base no tema anterior
    val backgroundColor = if (isDarkMode) WhiteBG else BackgroundDark

    LaunchedEffect(animationPhase) {
        Timber.tag("MainRoot").d("Animation phase changed: $animationPhase")
        when (animationPhase) {
            ThemeAnimationPhase.Expanding -> {
                delay(600)
                viewModel.toggleTheme()
                viewModel.updateThemeAnimationPhase(ThemeAnimationPhase.ThemeSwitched)
            }

            ThemeAnimationPhase.ThemeSwitched -> {
                delay(100)
                viewModel.updateThemeAnimationPhase(ThemeAnimationPhase.Collapsing)
            }

            ThemeAnimationPhase.Collapsing -> {
                delay(600)
                viewModel.updateThemeAnimationPhase(ThemeAnimationPhase.Idle)
            }

            ThemeAnimationPhase.Idle -> Unit
        }
    }

    CatholicLiturgyTheme(darkTheme = isDarkMode) {
        SetupSystemUi(rememberSystemUiController(), CatholicLiturgyColors.primary)

        Box(modifier = Modifier.fillMaxSize()) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = CatholicLiturgyColors.background
            ) {
                DestinationsNavHost(
                    navController = navController,
                    navGraph = NavGraphs.root,
                    dependenciesContainerBuilder = {
                        dependency(AppNavigationProvider(navController))
                    }
                )
            }

            // Círculo animado acima de todo conteúdo
            if (animationPhase != ThemeAnimationPhase.Idle) {
                Canvas(modifier = Modifier
                    .fillMaxSize()
                    .zIndex(2f)) {
                    drawCircle(
                        color = backgroundColor,
                        radius = animatedRadius,
                        center = themeButtonPosition
                    )
                }
            }
        }
    }
}
