package com.elfeky.devdash.ui.common.dialogs.company.components

import android.graphics.Bitmap
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
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.elfeky.devdash.R
import com.elfeky.devdash.ui.common.component.image_full_screen_card.ImageViewerDialog
import com.elfeky.devdash.ui.utils.ImageUtils.getBitmapFromUri


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompanyLogo(
    imageUrl: Bitmap?,
    modifier: Modifier = Modifier,
    @DrawableRes placeholderImage: Int = R.drawable.img_placeholder,
    onImageChanged: (Bitmap?) -> Unit
) {
    var isImageExpanded by remember { mutableStateOf(false) }
    val context = LocalContext.current

    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
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
        image = imageUrl,
        onClose = { isImageExpanded = false },
        onImageSelected = { uri ->
            val newBitmap = getBitmapFromUri(uri, context)
            onImageChanged(newBitmap)
            isImageExpanded = false
        },
        onImageDeleted = {
            isImageExpanded = false
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ImageSelectionDialog(
    isExpanded: Boolean,
    image: Bitmap?,
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
            imageUri = image,
            onClose = onClose,
            onChangeClick = { pickMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly)) },
            onDeleteClick = onImageDeleted
        )
    }
}