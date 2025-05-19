package com.elfeky.devdash.ui.screens.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.background
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import com.elfeky.devdash.ui.common.component.LoadingIndicator
import com.elfeky.devdash.ui.screens.details_screens.company.components.DetailsScreenAppBar
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.devdash.ui.utils.gradientBackground

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ScreenContainer(
    title: String,
    isPinned: Boolean,
    isLoading: Boolean,
    onDeleteClick: () -> Unit,
    onPinClick: () -> Unit,
    onEditClick: () -> Unit,
    onBackClick: () -> Unit,
    onCreateClick: () -> Unit,
    modifier: Modifier = Modifier,
    image: Any? = null,
    content: @Composable (paddingValues: PaddingValues, scrollBehavior: TopAppBarScrollBehavior) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    var fabVisible by remember { mutableStateOf(true) }

    val fabScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                if (consumed.y < 0 && fabVisible) {
                    fabVisible = false
                }
                if (consumed.y > 0 && !fabVisible) {
                    fabVisible = true
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
                onDeleteClick = onDeleteClick,
                onPinClick = onPinClick,
                onEditClick = onEditClick,
                onBackClick = onBackClick,
                scrollBehavior = scrollBehavior,
                image = image
            )
        },
        floatingActionButton = {
            AnimatedVisibility(
                visible = fabVisible,
                enter = fadeIn() + expandIn(),
                exit = fadeOut() + shrinkOut(animationSpec = tween(durationMillis = 150))
            ) {
                FloatingActionButton(
                    onClick = onCreateClick,
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
                }
            }
        }
    ) { padding ->
        Crossfade(
            targetState = isLoading,
            animationSpec = tween(300),
            modifier = Modifier
                .fillMaxSize()
                .padding(padding) // Apply the padding to the content
        ) { state ->
            when (state) {
                true -> LoadingIndicator()
                false -> content(
                    PaddingValues(),
                    scrollBehavior
                ) // Pass PaddingValues(), content will use the Scaffold's padding
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
            onDeleteClick = {},
            onPinClick = {},
            onEditClick = {},
            onBackClick = {},
            onCreateClick = {},
            modifier = Modifier.background(gradientBackground)
        ) { _, _ -> }
    }
}