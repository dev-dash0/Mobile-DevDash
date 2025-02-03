package com.elfeky.devdash.ui.common.dialogs.calender

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.ui.theme.Black
import com.elfeky.devdash.ui.theme.IceBlue
import com.elfeky.devdash.ui.theme.LightGray
import com.elfeky.devdash.ui.theme.Pink
import com.elfeky.devdash.ui.utils.localDateToString
import java.time.LocalDate

@Composable
fun DateSelectionField(
    label: String,
    date: LocalDate?,
    isSelected: Boolean,
    onClick: () -> Unit,
    onClear: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.clickable { onClick() },
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = label,
            color = if (isSelected) Pink else LightGray,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )

        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(IceBlue)
                .border(2.dp, if (isSelected) Pink else LightGray, RoundedCornerShape(8.dp))
                .clickable { onClick() }
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = date?.let { localDateToString(it) } ?: "Set date",
                color = Black
            )

            IconButton(
                onClick = if (date != null) onClear else onClick,
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "Clear Date",
                    modifier = Modifier.rotate(if (date != null) 0f else 45f),
                    tint = Black
                )
            }
        }
    }
}

@Preview
@Composable
private fun DateSelectionFieldPreview() {
    DateSelectionField(
        label = "Start Date",
        date = LocalDate.now(),
        isSelected = true,
        onClick = {},
        onClear = {})
}