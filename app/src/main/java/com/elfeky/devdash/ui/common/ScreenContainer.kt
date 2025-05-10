package com.elfeky.devdash.ui.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.elfeky.devdash.ui.common.component.LoadingIndicator
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.devdash.ui.utils.gradientBackground

@Composable
fun ScreenContainer(
    isLoading: Boolean,
    onCreateClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = onCreateClick,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
            }
        }
    ) { padding ->
        AnimatedVisibility(
            visible = isLoading,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            exit = fadeOut() + shrinkOut(shrinkTowards = Alignment.TopCenter)
        ) {
            LoadingIndicator()
        }

        AnimatedVisibility(
            visible = !isLoading,
            modifier = Modifier.fillMaxSize(),
            enter = fadeIn() + expandIn(expandFrom = Alignment.TopCenter)
        ) {
            content()
        }
    }
}

@Preview
@Composable
private fun ScreenContainerPreview() {
    DevDashTheme {
        ScreenContainer(true, {}, Modifier.background(gradientBackground)) {}
    }
}