package com.ronaldsantos.catholicliturgy.presentation.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination
import com.ronaldsantos.catholicliturgy.R
import com.ronaldsantos.catholicliturgy.app.component.DatePickerDialogWrapper
import com.ronaldsantos.catholicliturgy.app.component.SmallSpacer
import com.ronaldsantos.catholicliturgy.app.theme.CatholicLiturgyColors
import com.ronaldsantos.catholicliturgy.app.theme.CatholicLiturgyTypography
import com.ronaldsantos.catholicliturgy.app.theme.RedDark
import com.ronaldsantos.catholicliturgy.app.widget.CLToolbar
import com.ronaldsantos.catholicliturgy.app.widget.EmptyView
import com.ronaldsantos.catholicliturgy.app.widget.ErrorView
import com.ronaldsantos.catholicliturgy.app.widget.LoadingView
import com.ronaldsantos.catholicliturgy.domain.model.ReadingTypeDto
import com.ronaldsantos.catholicliturgy.domain.model.ReadingsDto
import com.ronaldsantos.catholicliturgy.library.framework.base.BaseViewState
import com.ronaldsantos.catholicliturgy.library.framework.extension.cast
import com.ronaldsantos.catholicliturgy.provider.navigation.NavigationProvider
import dev.jeziellago.compose.markdowntext.MarkdownText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber

@Destination(start = true)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    @Suppress("Unused")
    navigator: NavigationProvider,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val isDarkMode by viewModel.isDarkMode.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(viewModel) {
        viewModel.onTriggerEvent(HomeEvent.LoadDailyLiturgy())
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            CLToolbar(R.string.toolbar_home_title, elevation = 0.dp, actions = {
                IconButton(
                    onClick = {
                        Timber.d("Theme button clicked")
                        viewModel.onTriggerEvent(HomeEvent.ToggleThemeMode)
                    },
                    modifier = Modifier.onGloballyPositioned { coordinates ->
                        val position = coordinates.localToWindow(Offset.Zero)
                        viewModel.updateButtonPosition(
                            Offset(
                                x = position.x + coordinates.size.width / 2,
                                y = position.y + coordinates.size.height / 2,
                            )
                        )
                    }
                ) {
                    val iconResId = if (isDarkMode) {
                        R.drawable.ic_moon
                    } else {
                        R.drawable.ic_sun
                    }
                    Icon(painter = painterResource(id = iconResId), contentDescription = null)
                }
            })
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues)
            ) {
                HomePage(
                    uiState = uiState,
                    viewModel = viewModel,
                    paddingValues = paddingValues,
                    coroutineScope = coroutineScope,
                    modifier = modifier
                )
            }
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeContent(
    viewModel: HomeViewModel = hiltViewModel(),
    paddingValues: PaddingValues,
    viewState: HomeViewState,
    coroutineScope: CoroutineScope,
) {
    val tabsName by rememberSaveable {
        mutableStateOf(viewState.tabs.map { it.value })
    }

    val pagerState = rememberPagerState {
        tabsName.size
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = viewState.isRefreshing),
        onRefresh = { viewModel.onTriggerEvent(HomeEvent.LoadDailyLiturgy(isRefreshing = true)) },
        indicatorPadding = paddingValues,
        indicator = { state, trigger ->
            SwipeRefreshIndicator(state = state, refreshTriggerDistance = trigger, scale = true)
        }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            LiturgyHeader(viewState, viewModel)
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                containerColor = CatholicLiturgyColors.primary,
                indicator = { tabPositions ->
                    SecondaryIndicator(
                        modifier = Modifier
                            .tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                        color = RedDark
                    )
                }
            ) {
                tabsName.forEachIndexed { index, stringResourceId ->
                    Tab(
                        selected = index == pagerState.currentPage,
                        onClick = {
                            val pageIndex = when (stringResourceId) {
                                HomeTabs.FirstReading.value -> tabsName.indexOf(HomeTabs.FirstReading.value)
                                HomeTabs.Psalm.value -> tabsName.indexOf(HomeTabs.Psalm.value)
                                HomeTabs.SecondReading.value -> tabsName.indexOf(HomeTabs.SecondReading.value)
                                HomeTabs.Gospel.value -> tabsName.indexOf(HomeTabs.Gospel.value)
                                else -> 0
                            }
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(pageIndex)
                            }
                        },
                        modifier = Modifier.weight(1f), // Distribui o espaço igualmente entre as abas
                        text = {
                            Text(
                                text = stringResource(id = stringResourceId),
                                style = CatholicLiturgyTypography.headlineMedium,
                            )
                        }
                    )
                }
            }
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) {
                when (pagerState.currentPage) {
                    tabsName.indexOf(HomeTabs.FirstReading.value) -> {
                        val reading =
                            viewState.dailyLiturgy.readings.firstOrNull { it.type == ReadingTypeDto.FirstReading }
                        ReadingContent(reading)
                    }

                    tabsName.indexOf(HomeTabs.Psalm.value) -> {
                        val reading =
                            viewState.dailyLiturgy.readings.firstOrNull { it.type == ReadingTypeDto.Psalm }
                        ReadingContent(reading)
                    }

                    tabsName.indexOf(HomeTabs.SecondReading.value) -> {
                        val reading =
                            viewState.dailyLiturgy.readings.firstOrNull { it.type == ReadingTypeDto.SecondaryReading }
                        ReadingContent(reading)
                    }

                    tabsName.indexOf(HomeTabs.Gospel.value) -> {
                        val reading =
                            viewState.dailyLiturgy.readings.firstOrNull { it.type == ReadingTypeDto.Gospel }
                        ReadingContent(reading)
                    }
                }
            }
        }
    }
}

@Composable
private fun LiturgyHeader(
    viewState: HomeViewState,
    viewModel: HomeViewModel,
) {
    var showDatePicker by remember {
        mutableStateOf(false)
    }

    Surface(
        color = MaterialTheme.colorScheme.primary
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(
                text = viewState.dailyLiturgy.entryTitle,
                style = CatholicLiturgyTypography.bodyMedium
            )
            SmallSpacer()
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Cor litúrgica: ${viewState.dailyLiturgy.color}",
                    style = CatholicLiturgyTypography.labelMedium
                )
                Button(
                    onClick = { showDatePicker = true },
                    colors = ButtonDefaults.textButtonColors(contentColor = CatholicLiturgyColors.onPrimary),
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = viewState.dailyLiturgy.liturgyDate,
                            style = CatholicLiturgyTypography.labelMedium
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(imageVector = Icons.Filled.CalendarMonth, contentDescription = null)
                    }
                }
                if (showDatePicker) {
                    DatePickerDialogWrapper(
                        onDateSelected = {
                            viewModel.onTriggerEvent(HomeEvent.LoadDailyLiturgy(period = it))
                        },
                        onDismiss = { showDatePicker = false },
                    )
                }
            }
        }
    }
}

@Composable
private fun ReadingContent(reading: ReadingsDto?) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 4.dp, start = 16.dp, end = 16.dp, bottom = 4.dp)
    ) {
        item {
            reading?.let {
                MarkdownText(markdown = it.text)
            }
        }
    }
}

@Composable
private fun HomePage(
    uiState: BaseViewState<*>,
    viewModel: HomeViewModel,
    paddingValues: PaddingValues,
    coroutineScope: CoroutineScope,
    modifier: Modifier
) {
    when (uiState) {
        is BaseViewState.Data -> {
            HomeContent(
                viewModel = viewModel,
                paddingValues = paddingValues,
                viewState = uiState.cast<BaseViewState.Data<HomeViewState>>().value,
                coroutineScope = coroutineScope,
            )
        }

        BaseViewState.Empty -> EmptyView(modifier = modifier)
        is BaseViewState.Error -> ErrorView(
            e = uiState.cast<BaseViewState.Error>().throwable,
            action = {
                viewModel.onTriggerEvent(HomeEvent.RetryLoadDailyLiturgy)
            },
        )

        BaseViewState.Loading -> LoadingView()
    }

    LaunchedEffect(key1 = viewModel) {
        viewModel.onTriggerEvent(HomeEvent.LoadDailyLiturgy())
    }
}
