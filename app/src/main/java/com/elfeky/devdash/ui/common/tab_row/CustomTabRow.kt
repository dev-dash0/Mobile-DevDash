package com.elfeky.devdash.ui.common.tab_row

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.zIndex

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTabRow(
    tabs: List<String>,
    selectedTabIndex: Int,
    onTabClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    TabRow(
        selectedTabIndex = selectedTabIndex,
        containerColor = Color.Unspecified,
        contentColor = MaterialTheme.colorScheme.onBackground,
        indicator = { tabPositions: List<TabPosition> ->
            if (selectedTabIndex < tabPositions.size) {
                TabRowDefaults.PrimaryIndicator(
                    Modifier.tabIndicatorOffset(currentTabPosition = tabPositions[selectedTabIndex]),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        },
        modifier = modifier
    ) {
        tabs.forEachIndexed { index, item ->
            Tab(
                modifier = Modifier.zIndex(2f),
                selected = index == selectedTabIndex,
                onClick = { onTabClick(index) },
                text = { Text(text = item) },
                selectedContentColor = MaterialTheme.colorScheme.onBackground,
                unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}