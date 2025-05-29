package com.elfeky.devdash.ui.screens.details_screens.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
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
    content: @Composable (paddingValues: PaddingValues, scrollBehavior: TopAppBarScrollBehavior) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    var fabVisible by remember { mutableStateOf(true) }

    val fabScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                if (available.y < 0 && !fabVisible) {
                    fabVisible = true
                } else if (available.y > 0 && fabVisible) {
                    fabVisible = false
                }
                return Offset.Zero
            }
        }
    }

    Scaffold(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .nestedScroll(fabScrollConnection)
            .fillMaxSize(),
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
            AnimatedVisibility(
                visible = fabVisible,
                enter = fadeIn() + expandIn(expandFrom = Alignment.Center),
                exit = fadeOut() + shrinkOut(
                    shrinkTowards = Alignment.Center,
                    animationSpec = tween(durationMillis = 150)
                )
            ) {
                FloatingActionButton(
                    onClick = onCreateClick,
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "Create")
                }
            }
        }
    ) { paddingValues ->
        AnimatedContent(
            targetState = isLoading,
            modifier = Modifier.fillMaxSize()
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
                content(paddingValues, scrollBehavior)
            }
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
            modifier = Modifier.background(gradientBackground)
        ) { _, _ -> }
    }
}