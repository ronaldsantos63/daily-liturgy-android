package com.ronaldsantos.catholicliturgy.presentation.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
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
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination
import com.ronaldsantos.catholicliturgy.R
import com.ronaldsantos.catholicliturgy.app.component.DatePickerDialogWrapper
import com.ronaldsantos.catholicliturgy.app.component.MarkdownContent
import com.ronaldsantos.catholicliturgy.app.component.SmallSpacer
import com.ronaldsantos.catholicliturgy.app.theme.CatholicLiturgyColors
import com.ronaldsantos.catholicliturgy.app.theme.CatholicLiturgyTypography
import com.ronaldsantos.catholicliturgy.app.widget.CLToolbar
import com.ronaldsantos.catholicliturgy.app.widget.EmptyView
import com.ronaldsantos.catholicliturgy.app.widget.ErrorView
import com.ronaldsantos.catholicliturgy.app.widget.LoadingView
import com.ronaldsantos.catholicliturgy.domain.model.ReadingTypeDto
import com.ronaldsantos.catholicliturgy.domain.model.ReadingsDto
import com.ronaldsantos.catholicliturgy.library.framework.base.BaseViewState
import com.ronaldsantos.catholicliturgy.library.framework.extension.cast
import com.ronaldsantos.catholicliturgy.provider.navigation.NavigationProvider
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
        modifier = modifier
            .fillMaxSize()
            .background(CatholicLiturgyColors.background),
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
                    Icon(
                        painter = painterResource(id = iconResId),
                        contentDescription = stringResource(R.string.description_toggle_theme)
                    )
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
    val tabToReadingType = mapOf(
        HomeTabs.FirstReading.value to ReadingTypeDto.FirstReading,
        HomeTabs.Psalm.value to ReadingTypeDto.Psalm,
        HomeTabs.SecondReading.value to ReadingTypeDto.SecondaryReading,
        HomeTabs.Gospel.value to ReadingTypeDto.Gospel,
    )

    val tabsName = viewState.tabs.map { it.value }

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { tabsName.size }
    )

    val minFontSize = 12f
    val maxFontSize = 32f
    val defaultFontSize = 18f
//    var fontSize by rememberSaveable { mutableFloatStateOf(defaultFontSize) }
    var sliderValue by rememberSaveable { mutableFloatStateOf(18f) }
    val fontSize = sliderValue.toInt().sp

    LaunchedEffect(viewState.dailyLiturgy.liturgyDate) {
        pagerState.scrollToPage(0)
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
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 16.dp, vertical = 8.dp),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Column(modifier = Modifier.fillMaxWidth()) {
//                    Text(
//                        text = "Tamanho da fonte: ${sliderValue.toInt()}sp",
//                        style = CatholicLiturgyTypography.labelLarge
//                    )
//                    Slider(
//                        value = sliderValue,
//                        onValueChange = {
//                            sliderValue = it
////                            fontSize = it.sp
//                        },
//                        valueRange = minFontSize..maxFontSize,
//                        steps = 10
//                    )
//                }
//
//                TextButton(onClick = {
//                    viewModel.onTriggerEvent(HomeEvent.ReadAloud(viewState))
//                }) {
//                    Icon(
//                        Icons.AutoMirrored.Filled.VolumeUp,
//                        contentDescription = "Ler texto"
//                    )
//                }
//            }
            if (tabsName.isNotEmpty()) {
                ScrollableTabRow(
                    selectedTabIndex = pagerState.currentPage,
                    edgePadding = 16.dp,
                    indicator = { tabPositions ->
                        SecondaryIndicator(
                            modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage])
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
                            text = {
                                Text(
                                    text = stringResource(id = stringResourceId),
                                    style = CatholicLiturgyTypography.titleMedium.copy(fontSize = 18.sp),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    softWrap = false
                                )
                            }
                        )
                    }
                }
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize()
                ) {
                    val readingType = tabToReadingType[tabsName.getOrNull(pagerState.currentPage)]
                    val reading = viewState.dailyLiturgy.readings.firstOrNull { it.type == readingType }
                    ReadingContent(reading, fontSize)
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

    Surface {
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(
                text = viewState.dailyLiturgy.entryTitle,
                style = CatholicLiturgyTypography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold
                )
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
                    style = CatholicLiturgyTypography.labelLarge
                )
                TextButton(
                    onClick = { showDatePicker = true },
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = viewState.dailyLiturgy.liturgyDate,
                            style = CatholicLiturgyTypography.labelLarge,
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
private fun ReadingContent(reading: ReadingsDto?, fontSize: TextUnit) {
    SelectionContainer {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 4.dp, start = 16.dp, end = 16.dp, bottom = 4.dp)
        ) {
            item {
                reading?.let {
                    MarkdownContent(it.text, fontSize)
                }
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
