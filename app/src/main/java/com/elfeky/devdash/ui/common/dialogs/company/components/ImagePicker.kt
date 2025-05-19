package com.elfeky.devdash.ui.common.dialogs.company.components

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elfeky.devdash.R
import com.elfeky.devdash.ui.screens.details_screens.company.components.CompanyLogo
import com.elfeky.devdash.ui.theme.DarkBlue
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.devdash.ui.theme.White

@Composable
fun ImagePicker(onImageSelected: (Any?) -> Unit, image: Any? = null) {
    var selectedImageUri by remember { mutableStateOf<Any?>(image) }

    val pickMedia = rememberLauncherForActivityResult(PickVisualMedia()) { uri ->
        if (uri != null) {
            Log.d("PhotoPicker", "Selected URI: $uri")
            selectedImageUri = uri
            onImageSelected(uri)
        } else Log.d("PhotoPicker", "No media selected")
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box {
            CompanyLogo(
                selectedImageUri,
                modifier = Modifier.size(64.dp),
                onImageChanged = {
                    onImageSelected(it)
                    selectedImageUri = it
                }
            )

            if (selectedImageUri != null) {
                Icon(
                    Icons.Default.Close,
                    "delete image",
                    Modifier
                        .size(24.dp)
                        .align(Alignment.TopEnd)
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.onSecondary.copy(alpha = .5f),
                            CircleShape
                        )
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.onSecondary.copy(alpha = .7f))
                        .aspectRatio(1f)
                        .clickable {
                            onImageSelected(null)
                            selectedImageUri = null
                        }
                        .padding(4.dp),
                    MaterialTheme.colorScheme.onBackground
                )
            }

        }

        Button(
            onClick = { pickMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly)) },
            contentPadding = PaddingValues(8.dp),
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(
                containerColor = White,
                contentColor = DarkBlue
            )
        ) {
            Icon(painter = painterResource(R.drawable.upload_cloud), contentDescription = "Upload")
            Spacer(modifier = Modifier.width(8.dp))
            Text("Upload Photo")
        }
    }
}

@Preview
@Composable
private fun ImagePickerPreview() {
    DevDashTheme {
        ImagePicker({})
    }
}