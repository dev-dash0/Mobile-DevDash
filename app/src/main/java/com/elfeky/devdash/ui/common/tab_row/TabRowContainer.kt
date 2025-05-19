package com.elfeky.devdash.ui.common.tab_row

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerScope
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.elfeky.devdash.ui.theme.DevDashTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabRowContainer(
    tabs: List<String>,
    modifier: Modifier = Modifier,
    pageContent: @Composable (PagerScope.(page: Int) -> Unit)
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { tabs.size })

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomTabRow(
            tabs = tabs,
            selectedTabIndex = pagerState.currentPage,
            onTabClick = { index ->
                scope.launch { pagerState.animateScrollToPage(index) }
            }
        )

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            pageContent = pageContent
        )
    }
}

@Preview
@Composable
@OptIn(ExperimentalFoundationApi::class)
fun TabRowContainerPreview() {
    val sampleTabs = listOf("Tab 1", "Tab 2", "Tab 3")
    DevDashTheme {
        TabRowContainer(tabs = sampleTabs) { page ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Content for ${sampleTabs[page]}")
            }
        }
    }
}