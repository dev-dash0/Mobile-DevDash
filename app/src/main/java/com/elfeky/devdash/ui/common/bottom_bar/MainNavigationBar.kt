package com.elfeky.devdash.ui.common.bottom_bar

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elfeky.devdash.R
import com.elfeky.devdash.navigation.main_navigation.MainScreen
import com.elfeky.devdash.ui.theme.Lavender
import com.elfeky.devdash.ui.theme.NavyBlue
import com.elfeky.devdash.ui.theme.White
import com.elfeky.domain.model.BottomNavigationItem

@Composable
fun MainNavigationBar(
    isSelected: (route: String) -> Boolean,
    modifier: Modifier = Modifier,
    onItemClick: (route: String) -> Unit
) {

    val navigationItems = listOf(
        BottomNavigationItem(
            title = "Home",
            icon = R.drawable.home_ic,
            route = MainScreen.HomeScreen.route
        ),
        BottomNavigationItem(
            title = "Company",
            icon = R.drawable.company_ic,
            route = MainScreen.WorkSpaceScreen.route
        ),
        BottomNavigationItem(
            title = "Calender",
            icon = R.drawable.calender_ic,
            route = MainScreen.CalenderScreen.route
        ),
        BottomNavigationItem(
            title = "More",
            icon = R.drawable.more_1,
            route = MainScreen.MoreScreen.route
        ),
    )

    NavigationBar(
        containerColor = Lavender,
        modifier = modifier
            .clip(RoundedCornerShape(topStart = 36.dp, topEnd = 36.dp))
    ) {
        navigationItems.forEach { item ->
            NavigationBarItem(
                selected = isSelected(item.route),
                onClick = { onItemClick(item.route) },
                label = {
                    Text(
                        text = item.title,
                        fontSize = 16.sp,
                        overflow = TextOverflow.Visible,
                        maxLines = 1,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = NavyBlue,
                    selectedIconColor = White,
                    unselectedIconColor = Color.Black,
                    selectedTextColor = Color.Black,
                    unselectedTextColor = Color.Transparent,

                    ),
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = item.icon),
                        contentDescription = item.title,
                    )
                },
                modifier = Modifier.size(24.dp)
            )
        }
    }
}