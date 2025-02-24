package com.elfeky.devdash.ui.common.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.elfeky.devdash.R
import com.elfeky.devdash.ui.theme.DevDashTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    showIcons: Boolean = false
) {
    CenterAlignedTopAppBar(
        title = { Text(title) },
        modifier = modifier,
        navigationIcon = {
            if (showIcons)
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = "Back"
                    )
                }
        },
        actions = {
            if (showIcons) {
                IconButton(onClick = onBack) {
                    Icon(
                        painter = painterResource(R.drawable.sesrch_ic),
                        contentDescription = "Back"
                    )
                }
                IconButton(onClick = onBack) {
                    Icon(
                        painter = painterResource(R.drawable.user_add_ic),
                        contentDescription = "Back"
                    )
                }
            }
            IconButton(onClick = onBack) {
                Icon(
                    painter = painterResource(R.drawable.user_ic),
                    contentDescription = "Back"
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Transparent,
            navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
            titleContentColor = MaterialTheme.colorScheme.onBackground,
            actionIconContentColor = MaterialTheme.colorScheme.onBackground,
        )
    )
}

@Preview
@Composable
private fun TopBarPreview() {
    DevDashTheme {
        TopBar("Home", {})
    }
}