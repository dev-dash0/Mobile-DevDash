package com.elfeky.devdash.ui.common.dialogs.company.components

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.rememberAsyncImagePainter
import com.elfeky.devdash.R
import com.elfeky.devdash.ui.theme.DarkBlue
import com.elfeky.devdash.ui.theme.Gray
import com.elfeky.devdash.ui.theme.White

@Composable
fun ImagePicker(onImageSelected: (Uri) -> Unit) {
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var showDialog by remember { mutableStateOf(false) }

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
        Image(
            painter = selectedImageUri?.let { rememberAsyncImagePainter(it) }
                ?: painterResource(R.drawable.image_placeholder),
            contentDescription = "Selected or Placeholder Image",
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .border(2.dp, Gray, CircleShape)
                .clickable {
                    if (selectedImageUri != null) showDialog = true
                    else pickMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
                }
        )

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

    if (showDialog) {
        Dialog(onDismissRequest = { showDialog = false }) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { showDialog = false }
            ) {
                selectedImageUri?.let { uri ->
                    Image(
                        painter = rememberAsyncImagePainter(uri),
                        contentDescription = "Full Image",
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}
