package com.elfeky.devdash.ui.screens.main_screens.more.components.profile_screen.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elfeky.devdash.ui.theme.DevDashTheme

@Composable
fun ProfileInfoItem(modifier: Modifier = Modifier, primaryName: String, secondaryName: String) {

    Column(modifier = modifier.padding(4.dp)) {
        Text(
            text = primaryName,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = modifier.padding(bottom = 8.dp)
        )
        Text(
            text = secondaryName,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray,
            modifier = modifier.padding(bottom = 4.dp)
        )
        HorizontalDivider(Modifier.padding(4.dp))
    }
}

@Preview
@Composable
private fun ProfileInfoItemPreview() {
    DevDashTheme {
        ProfileInfoItem(primaryName = "Full Name", secondaryName = "Youssef Elfeky")
    }

}