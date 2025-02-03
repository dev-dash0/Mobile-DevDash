package com.elfeky.devdash.ui.common.dialogs.issue

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.R
import com.elfeky.devdash.ui.theme.LightGray

@Composable
fun AttachmentRow(modifier: Modifier = Modifier) {
    IconButton(
        onClick = { /* Handle date picker */ },
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.fillMaxWidth(.9f)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_pin),
                contentDescription = "Add attachments",
                tint = LightGray
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Add attachments", color = LightGray)
        }
    }
}