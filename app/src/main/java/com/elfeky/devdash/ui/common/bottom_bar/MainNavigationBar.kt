package com.elfeky.devdash.ui.screens.main_screens.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.elfeky.devdash.R
import com.elfeky.devdash.ui.theme.Lavender
import com.elfeky.devdash.ui.theme.NavyBlue
import com.elfeky.devdash.ui.theme.White
import com.elfeky.domain.model.BottomNavigationItem

@Composable
fun MainNavigationBar(navController: NavController, modifier: Modifier = Modifier) {

    val navigationItems = listOf(
        BottomNavigationItem(
            title = "Home",
            icon = R.drawable.home_ic,
        ),
        BottomNavigationItem(
            title = "Company",
            icon = R.drawable.company_ic,
        ),
        BottomNavigationItem(
            title = "Calender",
            icon = R.drawable.calender_ic,
        ),
        BottomNavigationItem(
            title = "More",
            icon = R.drawable.more_1,
        ),
    )

    var selectedItemIndex by remember {
        mutableIntStateOf(0)
    }

    NavigationBar(
        containerColor = Lavender,
        modifier = modifier
            .clip(RoundedCornerShape(topStart = 36.dp, topEnd = 36.dp))
    ) {
        navigationItems.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = {
                    selectedItemIndex = index
                    navController.navigate(item.title)
                },
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