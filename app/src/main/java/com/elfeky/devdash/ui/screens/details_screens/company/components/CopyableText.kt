package com.elfeky.devdash.ui.screens.details_screens.company.components

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.elfeky.devdash.R
import com.elfeky.devdash.ui.theme.DevDashTheme
import okhttp3.internal.wait

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CopyableText(
    text: String,
    modifier: Modifier = Modifier
) {
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current
    var showToast by remember { mutableStateOf(false) }

    LaunchedEffect(showToast) {
        if (showToast) {
            Toast.makeText(context, "$text copied to clipboard", Toast.LENGTH_SHORT).show().wait()
            showToast = false
        }
    }

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

        IconButton({ clipboardManager.setText(AnnotatedString(text)) }) {
            Icon(painter = painterResource(R.drawable.copy), "copy")
        }
    }
}

@Preview
@Composable
private fun CopyableTextPreview() {
    DevDashTheme {
        CopyableText(text = "https://previewcompany.com")
    }
}