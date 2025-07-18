package com.elfeky.devdash.ui.screens.details_screens.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.elfeky.devdash.R
import com.elfeky.devdash.ui.theme.DevDashTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreenAppBar(
    title: String,
    isPinned: Boolean,
    isOwner: Boolean,
    onDeleteClick: () -> Unit,
    onPinClick: () -> Unit,
    onEditClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    image: Any? = null,
    hasImageBackground: Boolean = false,
    @DrawableRes placeholderImage: Int = R.drawable.tenant_placeholder_img,
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    val collapsedFraction = scrollBehavior?.state?.collapsedFraction ?: 0f
    val maxLines by remember { derivedStateOf { if (collapsedFraction > 0.5f) 1 else Int.MAX_VALUE } }
    val contentColor = MaterialTheme.colorScheme.onBackground

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(image)
            .crossfade(true)
            .error(placeholderImage)
            .build()
    )

    val appBarModifier = if (hasImageBackground) {
        modifier.drawWithCache {
            val scrimBrush = Brush.verticalGradient(
                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.6f)),
                startY = size.height * 0.5f,
                endY = size.height
            )
            onDrawBehind {
                with(painter) {
                    draw(size = size, alpha = 0.8f)
                }

                drawRect(brush = scrimBrush)
            }


        }
    } else {
        modifier
    }

    val expandedHeight =
        if (hasImageBackground) 300.dp else TopAppBarDefaults.LargeAppBarExpandedHeight

    LargeTopAppBar(
        expandedHeight = expandedHeight,
        collapsedHeight = TopAppBarDefaults.LargeAppBarCollapsedHeight,
        modifier = appBarModifier,
        title = {
            AppBarTitle(
                title = title,
                maxLines = maxLines,
                contentColor = contentColor
            )
        },
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = Color.Transparent,
            titleContentColor = contentColor,
            navigationIconContentColor = contentColor,
            actionIconContentColor = contentColor,
        ),
        navigationIcon = {
            AppBarBackButton(onBackClick = onBackClick, contentColor = contentColor)
        },
        actions = {
            AppBarActions(
                isPinned = isPinned,
                isOwner = isOwner,
                onPinClick = onPinClick,
                onDeleteClick = onDeleteClick,
                onEditClick = onEditClick,
                contentColor = contentColor
            )
        },
        scrollBehavior = scrollBehavior
    )
}

@Composable
private fun AppBarTitle(
    title: String,
    maxLines: Int,
    contentColor: Color
) {
    Text(
        text = title,
        modifier = Modifier.padding(start = 24.dp),
        style = MaterialTheme.typography.headlineMedium,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        color = contentColor
    )
}

@Composable
private fun AppBarBackButton(
    onBackClick: () -> Unit,
    contentColor: Color
) {
    IconButton(onClick = onBackClick) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
            tint = contentColor
        )
    }
}

@Composable
private fun AppBarActions(
    isPinned: Boolean,
    isOwner: Boolean,
    onPinClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onEditClick: () -> Unit,
    contentColor: Color
) {
    AppBarAction(
        onClick = onPinClick,
        iconResId = if (isPinned) R.drawable.ic_un_pin else R.drawable.ic_pin,
        contentDescription = "Pin",
        tint = contentColor
    )

    if (isOwner) {
        AppBarAction(
            onClick = onDeleteClick,
            iconResId = R.drawable.ic_trash,
            contentDescription = "Delete",
            tint = MaterialTheme.colorScheme.error
        )
        AppBarAction(
            onClick = onEditClick,
            iconResId = R.drawable.ic_edit,
            contentDescription = "Edit",
            tint = contentColor
        )
    }
}

@Composable
private fun AppBarAction(
    onClick: () -> Unit,
    @DrawableRes iconResId: Int,
    contentDescription: String?,
    tint: Color
) {
    IconButton(onClick = onClick) {
        Icon(
            painter = painterResource(id = iconResId),
            contentDescription = contentDescription,
            tint = tint
        )
    }
}

@Preview
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DetailsScreenAppBarPreview() {
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    DevDashTheme {
        DetailsScreenAppBar(
            title = "This is a long company name that might wrap when expanded",
            isPinned = false,
            isOwner = true,
            onDeleteClick = { },
            onPinClick = { },
            onEditClick = { },
            onBackClick = { },
            scrollBehavior = scrollBehavior
        )
    }
}