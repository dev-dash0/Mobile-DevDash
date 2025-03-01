package com.elfeky.devdash.ui.common.card.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.R

@Composable
fun CardHeader(
    projectName: String,
    date: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Light)
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = projectName, style = textStyle)
        Row(modifier = Modifier.padding(horizontal = 8.dp), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            Icon(
                painter = painterResource(R.drawable.calender_ic),
                contentDescription = "Date",
                modifier = Modifier.size(18.dp)
            )
        Text(text = date, style = textStyle)
        }
    }
}