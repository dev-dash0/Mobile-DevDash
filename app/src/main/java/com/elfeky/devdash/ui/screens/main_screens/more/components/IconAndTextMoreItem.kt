package com.elfeky.devdash.ui.screens.main_screens.more.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elfeky.devdash.ui.theme.DevDashTheme

@Composable
fun IconAndTextMoreItem(
    modifier: Modifier = Modifier,
    primaryText: String,
    secondaryText: String,
    logo: ImageVector,
    contentDescription: String,
    onItemClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp)
            .clickable {
                onItemClick()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.surface)

        ) {
            Image(
                imageVector = logo,
                contentDescription = contentDescription,
                modifier = modifier.align(Alignment.Center)
            )
        }
        Column(modifier = modifier.padding(start = 16.dp)) {
            Text(
                text = primaryText,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = modifier.padding(top = 4.dp))
            Text(text = secondaryText, fontSize = 16.sp, color = Color.Gray)

        }
        Spacer(modifier = Modifier.weight(1f))

        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = "next",
            modifier = modifier
                .size(20.dp),
            tint = MaterialTheme.colorScheme.onBackground
        )
    }

}


@Preview(showBackground = false)
@Composable
private fun ProfileItemPreview() {
    DevDashTheme {
        IconAndTextMoreItem(
            primaryText = "Personal Information",
            secondaryText = "Your information",
            logo = Icons.Default.ManageAccounts,
            contentDescription = "",
        ) {

        }
    }

}