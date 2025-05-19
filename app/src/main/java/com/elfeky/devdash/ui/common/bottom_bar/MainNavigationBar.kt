package com.elfeky.devdash.ui.common.bottom_bar

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
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
    isSelected: (route: String) -> Boolean,
    modifier: Modifier = Modifier,
    onItemClick: (route: String) -> Unit
) {
    NavigationBar(
        containerColor = Lavender,
        modifier = modifier
            .padding(horizontal = 16.dp)
            .navigationBarsPadding()
            .clip(RoundedCornerShape(24.dp))
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
                alwaysShowLabel = false,
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = NavyBlue,
                    selectedIconColor = White,
                    unselectedIconColor = Color.Black,
                    selectedTextColor = Color.Black
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