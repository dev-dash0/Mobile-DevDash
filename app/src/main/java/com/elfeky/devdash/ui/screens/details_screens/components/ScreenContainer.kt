package com.elfeky.devdash.ui.screens.details_screens.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import com.elfeky.devdash.ui.common.component.LoadingIndicator
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.devdash.ui.utils.gradientBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenContainer(
    title: String,
    isPinned: Boolean,
    isLoading: Boolean,
    isOwner: Boolean,
    onDeleteClick: () -> Unit,
    onPinClick: () -> Unit,
    onEditClick: () -> Unit,
    onBackClick: () -> Unit,
    onCreateClick: () -> Unit,
    modifier: Modifier = Modifier,
    image: Any? = null,
    hasImageBackground: Boolean = false,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    content: @Composable (paddingValues: PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(gradientBackground)
            .then(
                if (scrollBehavior != null)
                    modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
                else modifier
            ),
        topBar = {
            DetailsScreenAppBar(
                title = title,
                isPinned = isPinned,
                isOwner = isOwner,
                onDeleteClick = onDeleteClick,
                onPinClick = onPinClick,
                onEditClick = onEditClick,
                onBackClick = onBackClick,
                scrollBehavior = scrollBehavior,
                image = image,
                hasImageBackground = hasImageBackground
            )
        },
        floatingActionButton = {
            CreateFloatingActionButton(onCreateClick = onCreateClick)
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = Color.Unspecified
    ) { paddingValues ->
        ScreenContentWithLoading(
            isLoading = isLoading,
            paddingValues = paddingValues,
            content = content
        )
    }
}

@Composable
fun CreateFloatingActionButton(onCreateClick: () -> Unit) {
    FloatingActionButton(
        onClick = onCreateClick,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    ) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "Create")
    }
}

@Composable
private fun ScreenContentWithLoading(
    isLoading: Boolean,
    paddingValues: PaddingValues,
    content: @Composable (paddingValues: PaddingValues) -> Unit
) {
    AnimatedContent(
        targetState = isLoading,
        modifier = Modifier.fillMaxSize(), label = "ScreenContentLoadingAnimation"
    ) { targetIsLoading ->
        if (targetIsLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                LoadingIndicator()
            }
        } else {
            content(paddingValues)
        }
    }
}

@Preview
@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun ScreenContainerPreview() {
    DevDashTheme {
        ScreenContainer(
            title = "Company A",
            isPinned = false,
            isLoading = true,
            isOwner = true,
            onDeleteClick = {},
            onPinClick = {},
            onEditClick = {},
            onBackClick = {},
            onCreateClick = {},
        ) { _ -> }
    }
}