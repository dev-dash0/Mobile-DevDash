package com.elfeky.devdash.ui.screens.details_screens.company.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.elfeky.devdash.R
import com.elfeky.devdash.ui.theme.DevDashTheme

@Composable
fun CopyableText(
    text: String,
    modifier: Modifier = Modifier,
    onCopyClick: () -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 14.sp,
            overflow = TextOverflow.Ellipsis
        )

        IconButton(onClick = onCopyClick) {
            Icon(
                painter = painterResource(R.drawable.copy),
                contentDescription = "Copy text to clipboard",
                tint = MaterialTheme.colorScheme.secondary,
            )
        }
    }
}

@Preview
@Composable
private fun CopyableTextPreview() {
    DevDashTheme {
        CopyableText(text = "https://previewcompany.com") {}
    }
}