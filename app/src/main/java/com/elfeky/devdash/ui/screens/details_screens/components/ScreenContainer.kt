package com.elfeky.devdash.ui.screens.details_screens.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
//    onInviteClick: () -> Unit,
    onCreateClick: () -> Unit,
    modifier: Modifier = Modifier,
    image: Any? = null,
    hasImageBackground: Boolean = false,
    onChatWithAIClick: (() -> Unit)? = null,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    content: @Composable (paddingValues: PaddingValues) -> Unit
) {
    var showMoreButtons by remember { mutableStateOf(false) }
    val rotationAngle by animateFloatAsState(
        targetValue = if (showMoreButtons) 45f else 0f,
        label = "FabRotationAnimation"
    )

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
            if (isOwner) {
                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    AnimatedVisibility(
                        visible = showMoreButtons,
                        enter = fadeIn() + slideInVertically { it },
                        exit = fadeOut() + slideOutVertically { it }
                    ) {
                        Column(
                            horizontalAlignment = Alignment.End,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            if (onChatWithAIClick != null) {
                                SmallFloatingActionButton(
                                    onClick = {
                                        onChatWithAIClick()
                                        showMoreButtons = !showMoreButtons
                                    }
                                ) {
                                    Icon(Icons.Default.AutoAwesome, "Chat with AI")
                                }
                            }

                            SmallFloatingActionButton(
                                onClick = {
                                    onCreateClick()
                                    showMoreButtons = !showMoreButtons
                                }
                            ) {
                                Icon(Icons.Default.Add, "Add")
                            }
//                            SmallFloatingActionButton(onClick = onInviteClick) {
//                                Icon(Icons.Default.PersonAdd, "Invite")
//                            }
                        }
                    }

                    FloatingActionButton(
                        onClick = { showMoreButtons = !showMoreButtons },
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ) {
                        Icon(Icons.Filled.Add, "Expand", Modifier.rotate(rotationAngle))
                    }
                }
            }
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
//            onInviteClick = {},
            onChatWithAIClick = {},
            onCreateClick = {},
        ) { _ -> }
    }
}