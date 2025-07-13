package com.elfeky.devdash.ui.screens.details_screens.company.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.elfeky.devdash.R
import com.elfeky.devdash.ui.common.component.image_full_screen_card.ImageViewerDialog
import com.elfeky.devdash.ui.theme.DevDashTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Logo(
    imageUrl: Any?,
    modifier: Modifier = Modifier,
    @DrawableRes placeholderImage: Int = R.drawable.img_placeholder,
    onImageChanged: (Any?) -> Unit,
) {
    var selectedImageUri by remember(imageUrl) { mutableStateOf<Any?>(imageUrl) }
    var isImageExpanded by remember { mutableStateOf(false) }

    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(selectedImageUri)
            .crossfade(true)
            .error(placeholderImage)
            .placeholder(placeholderImage)
            .build(),
        contentDescription = "Company Logo",
        contentScale = ContentScale.Crop,
        modifier = modifier
            .clip(CircleShape)
            .aspectRatio(1f)
            .clickable { isImageExpanded = true }
    )

    ImageSelectionDialog(
        isExpanded = isImageExpanded,
        imageUri = selectedImageUri,
        onClose = { isImageExpanded = false },
        onImageSelected = { uri ->
            selectedImageUri = uri
            onImageChanged(uri)
            isImageExpanded = false
        },
        onImageDeleted = {
            selectedImageUri = null
            onImageChanged(null)
            isImageExpanded = false
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ImageSelectionDialog(
    isExpanded: Boolean,
    imageUri: Any?,
    onClose: () -> Unit,
    onImageSelected: (Uri?) -> Unit,
    onImageDeleted: () -> Unit,
) {
    val pickMedia = rememberLauncherForActivityResult(PickVisualMedia()) { uri ->
        onImageSelected(uri)
    }

    AnimatedVisibility(
        visible = isExpanded,
        enter = fadeIn(animationSpec = tween(durationMillis = 300)),
        exit = fadeOut(animationSpec = tween(durationMillis = 300))
    ) {
        ImageViewerDialog(
            imageUri = imageUri,
            onClose = onClose,
            onChangeClick = { pickMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly)) },
            onDeleteClick = onImageDeleted
        )
    }
}


@Preview
@Composable
fun CompanyLogoPreview() {
    DevDashTheme {
        Logo(
            imageUrl = null,
            onImageChanged = { /* Preview action */ }
        )
    }
}