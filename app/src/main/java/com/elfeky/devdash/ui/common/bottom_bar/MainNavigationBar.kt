package com.elfeky.devdash.ui.screens.main_screens.components.bottom_bar

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
import com.elfeky.devdash.ui.theme.Lavender
import com.elfeky.devdash.ui.theme.NavyBlue
import com.elfeky.devdash.ui.theme.White

@Composable
fun MainNavigationBar(
    isSelected: (item: BottomNavItem<out Any>) -> Boolean,
    onItemClick: (item: BottomNavItem<out Any>) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        containerColor = Lavender,
        modifier = modifier.clip(RoundedCornerShape(topStart = 36.dp, topEnd = 36.dp))
    ) {
            navigationItems.forEach { item ->
            NavigationBarItem(
                selected = isSelected(item),
                onClick = { onItemClick(item) },
                label = {
                    Text(
                        text = item.title,
                        fontSize = 16.sp,
                        overflow = TextOverflow.Visible,
                        maxLines = 1,
                        fontWeight = FontWeight.Bold
                    )
                },
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = item.icon),
                        contentDescription = item.title,
                        modifier = Modifier.size(24.dp)
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = NavyBlue,
                    selectedIconColor = White,
                    unselectedIconColor = Color.Black,
                    selectedTextColor = Color.Black,
                    unselectedTextColor = Color.Transparent
                )
            )
        }
    }
}