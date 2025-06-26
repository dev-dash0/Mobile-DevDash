import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


data class InboxItem(
    val id: Int,
    val title: String,
    val date: String,
    val isRead: Boolean
)

data class BottomNavItem(
    val title: String,
    val icon: @Composable () -> Unit
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinalInboxScreen() {
    val darkBackground = Color(0xFF08152B)
    val cardAndChipBackground = Color(0xFF443860)
    val selectedChipColor = Color(0x99EBDBFF)
    val bottomBarColor = Color(0xFF282845)
    val selectedBottomItemColor = Color(0xFF5F5DAB)


    val filterChips = listOf("All", "Mentions", "Assigned to me", "Unread")
    var selectedFilterChip by remember { mutableStateOf(filterChips[0]) }

    val inboxItems = remember {
        mutableStateOf(
            listOf(
                InboxItem(1, "Eng Doha updated an issues", "5 Nov", false),
                InboxItem(2, "Eng Menna updated an issues", "5 Nov", true),
                InboxItem(3, "Mr. ali updated an issues", "5 Nov", false),
                InboxItem(4, "Someone mentioned you", "4 Nov", true),
                InboxItem(5, "Task X assigned to you", "3 Nov", false)
            )
        )
    }

    val bottomNavItems = listOf(
        BottomNavItem("Home", { Icon(Icons.Filled.Home, contentDescription = "Home") }),
        BottomNavItem("Company", { Icon(Icons.Filled.LocationCity, contentDescription = "Company") }),
        BottomNavItem("Calendar", { Icon(Icons.Filled.DateRange, contentDescription = "Calendar") }),
        BottomNavItem("In Box", { Icon(Icons.Filled.MailOutline, contentDescription = "In Box") })
    )
    var selectedBottomNavItem by remember { mutableStateOf(bottomNavItems[3].title) }

    Scaffold(
        containerColor = darkBackground,
        bottomBar = {
            NavigationBar(
                modifier = Modifier.padding(bottom = 16.dp),
                containerColor = bottomBarColor
            ) {
                bottomNavItems.forEach { item ->
                    NavigationBarItem(
                        icon = item.icon,
                        label = { Text(item.title) },
                        selected = selectedBottomNavItem == item.title,
                        onClick = { selectedBottomNavItem = item.title },
                        colors = androidx.compose.material3.NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.White,
                            unselectedIconColor = Color.LightGray,
                            selectedTextColor = Color.White,
                            unselectedTextColor = Color.LightGray,
                            indicatorColor = selectedBottomItemColor
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                filterChips.forEach { chipText ->
                    FilterChip(
                        selected = selectedFilterChip == chipText,
                        onClick = { selectedFilterChip = chipText },
                        label = { Text(chipText, fontSize = 12.sp, fontWeight = FontWeight.Medium) },
                        colors = FilterChipDefaults.filterChipColors(
                            containerColor = cardAndChipBackground,
                            labelColor = Color.LightGray,
                            selectedContainerColor = selectedChipColor,
                            selectedLabelColor = Color.White
                        ),
                        border = null,
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.height(28.dp)
                    )
                }
            }


            Spacer(modifier = Modifier.height(16.dp))


            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(inboxItems.value.filter {
                    when (selectedFilterChip) {
                        "All" -> true
                        "Mentions" -> it.title.contains("mentioned", ignoreCase = true)
                        "Assigned to me" -> it.title.contains("assigned", ignoreCase = true)
                        "Unread" -> !it.isRead
                        else -> true
                    }
                }) { item ->
                    OvalInboxItemCard(item = item)
                }
            }
        }
    }
}

@Composable
fun OvalInboxItemCard(item: InboxItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { },

        shape = RoundedCornerShape(50.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF282845)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = item.title,
                color = Color.White,
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal
            )

            Text(
                text = item.date,
                color = Color.LightGray,
                fontSize = 13.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFinalInboxScreen() {
    MaterialTheme {
        Surface(color = Color(0xFF1A1A2F)) {
            FinalInboxScreen()
        }
    }
}