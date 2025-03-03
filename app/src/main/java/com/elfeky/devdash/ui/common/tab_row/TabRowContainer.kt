package com.elfeky.devdash.ui.common.tab_row

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerScope
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.zIndex
import kotlinx.coroutines.launch

@Composable
fun TabRowContainer(
    modifier: Modifier = Modifier,
    pageContent: @Composable (PagerScope.(page: Int) -> Unit)
) {
    val tabItems = listOf("Board", "Sprint", "Timeline")
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { tabItems.size })

    Column(
        modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground,
            indicator = { tabPositions: List<TabPosition> ->
                if (pagerState.currentPage < tabPositions.size) {
                    TabRowDefaults.PrimaryIndicator(
                        Modifier.tabIndicatorOffset(currentTabPosition = tabPositions[pagerState.currentPage]),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            },
            divider = {}
        ) {
            tabItems.forEachIndexed { index, item ->
                Tab(
                    modifier = Modifier.zIndex(2f),
                    selected = index == pagerState.currentPage,
                    onClick = { scope.launch { pagerState.animateScrollToPage(index) } },
                    text = { Text(text = item) },
                    selectedContentColor = MaterialTheme.colorScheme.onBackground,
                    unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            pageContent = pageContent
        )
    }
}