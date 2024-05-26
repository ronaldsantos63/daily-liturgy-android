package com.ronaldsantos.catholicliturgy.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ronaldsantos.catholicliturgy.R
import com.ronaldsantos.catholicliturgy.app.theme.CatholicLiturgyColors
import com.ronaldsantos.catholicliturgy.app.theme.CatholicLiturgyTypography
import com.ronaldsantos.catholicliturgy.app.theme.RalewayFonts
import com.ronaldsantos.catholicliturgy.app.theme.RedDark
import com.ronaldsantos.catholicliturgy.app.theme.selectedBottomItemColor
import com.ronaldsantos.catholicliturgy.app.theme.unselectedBottomItemColor
import com.ronaldsantos.catholicliturgy.app.widget.CLToolbar
import com.ronaldsantos.catholicliturgy.provider.navigation.NavigationProvider

@OptIn(ExperimentalMaterialApi::class)
@Destination(start = true)
@Composable
fun HomeScreen(navigator: NavigationProvider) {
    val scaffoldState = rememberScaffoldState()
    val (currentBottomTab, setCurrentBottomTab) = rememberSaveable {
        mutableStateOf(BottomBarHomeItem.CHARACTERS)
    }
    val bottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    Scaffold(
        scaffoldState = scaffoldState,
//            bottomBar = { HomeBottomNavigation(bottomTab, setCurrentBottomTab) },
        content = {
            Column(
                modifier = Modifier.padding(it)
            ) {
                val tabsName = remember { HomeTabs.entries.map { tab -> tab.value } }
                var selectedIndex by rememberSaveable { mutableIntStateOf(HomeTabs.FirstReading.ordinal) }
                TabRow(
                    selectedTabIndex = selectedIndex,
                    backgroundColor = CatholicLiturgyColors.primary,
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            color = RedDark,
                            height = TabRowDefaults.IndicatorHeight,
                            modifier = Modifier
                                .tabIndicatorOffset(tabPositions[selectedIndex])
                        )
                    }
                ) {
                    tabsName.forEachIndexed { index, stringResourceId ->
                        Tab(
                            selected = index == selectedIndex,
                            onClick = {
                                when (stringResourceId) {
                                    HomeTabs.FirstReading.value -> {
                                        selectedIndex = HomeTabs.FirstReading.ordinal
                                    }
                                    HomeTabs.Psalm.value -> {
                                        selectedIndex = HomeTabs.Psalm.ordinal
                                    }
                                    HomeTabs.SecondReading.value -> {
                                        selectedIndex = HomeTabs.SecondReading.ordinal
                                    }
                                    HomeTabs.Gospel.value -> {
                                        selectedIndex = HomeTabs.Gospel.ordinal
                                    }
                                }
                            },
                            text = {
                                Text(
                                    text = stringResourceId,
                                    style = CatholicLiturgyTypography.headlineMedium
                                )
                            }
                        )
                    }
                }
                when (selectedIndex) {
                    HomeTabs.FirstReading.ordinal -> {
//                            CharactersPage(uiState, viewModel, padding, navigator, modifier)
                    }
                    HomeTabs.Psalm.ordinal -> {
//                            FavoritesPage(
//                                coroutineScope,
//                                bottomSheetState,
//                                uiState,
//                                viewModel,
//                                navigator,
//                                modifier,
//                                selectedFavorite
//                            )
                    }
                }
            }

//                when (bottomTab) {
//                    BottomBarHomeItem.CHARACTERS -> CharactersScreen(
//                        modifier = modifier,
//                        navigator = navigator,
//                        bottomSheetState = bottomSheetState
//                    )
//                    BottomBarHomeItem.EPISODES -> EpisodesScreen(
//                        modifier = modifier,
//                        navigator = navigator
//                    )
//                    BottomBarHomeItem.LOCATIONS -> LocationsScreen(
//                        modifier = modifier,
//                        navigator = navigator
//                    )
//                    BottomBarHomeItem.SETTINGS -> SettingsScreen(
//                        modifier = modifier,
//                        navigator = navigator
//                    )
//                }
        }
    )
}

private enum class HomeTabs(val value: String) {
    FirstReading("1ª Leitura"),
    Psalm("Salmo"),
    SecondReading("2ª Leitura"),
    Gospel("Evangelho");
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun HomeBody(
    modifier: Modifier = Modifier,
    bottomSheetState: ModalBottomSheetState,
    sheetContent: @Composable ColumnScope.() -> Unit,
    pageContent: @Composable (PaddingValues) -> Unit
) {
    ModalBottomSheetLayout(
        sheetContent = sheetContent,
        modifier = modifier
            .fillMaxSize(),
        sheetState = bottomSheetState,
        sheetContentColor = CatholicLiturgyColors.background,
        sheetShape = RectangleShape,
        content = {
            Scaffold(
                topBar = { CLToolbar(R.string.toolbar_characters_title) },
                content = { pageContent.invoke(it) }
            )
        }
    )
}

@Composable
private fun HomeBottomNavigation(
    bottomTab: BottomBarHomeItem,
    setCurrentBottomTab: (BottomBarHomeItem) -> Unit
) {
    val bottomBarHeight = 56.dp
    val pages = BottomBarHomeItem.entries.toTypedArray()

    BottomNavigation(
        backgroundColor = CatholicLiturgyColors.primary,
        modifier = Modifier
            .fillMaxWidth()
            .windowInsetsBottomHeight(WindowInsets.navigationBars)
    ) {
        pages.forEach { page ->
            val selected = page == bottomTab
            val selectedLabelColor = if (selected) {
                selectedBottomItemColor
            } else {
                unselectedBottomItemColor
            }
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = rememberVectorPainter(image = page.icon),
                        contentDescription = stringResource(page.title)
                    )
                },
                label = {
                    Text(
                        text = stringResource(page.title),
                        color = selectedLabelColor,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = RalewayFonts
                    )
                },
                selected = selected,
                onClick = {
                    setCurrentBottomTab.invoke(page)
                },
                selectedContentColor = selectedBottomItemColor,
                unselectedContentColor = unselectedBottomItemColor,
                alwaysShowLabel = true,
                modifier = Modifier.navigationBarsPadding()
            )
        }
    }
}
