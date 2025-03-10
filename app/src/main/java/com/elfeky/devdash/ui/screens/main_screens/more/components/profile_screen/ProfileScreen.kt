package com.elfeky.devdash.ui.screens.main_screens.more.components.profile_screen

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.elfeky.devdash.ui.screens.main_screens.more.MoreViewModel
import com.elfeky.devdash.ui.screens.main_screens.more.components.profile_screen.component.ProfileInfoItem
import com.elfeky.devdash.ui.screens.main_screens.more.components.profile_screen.component.ProfileSymbol
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.google.firebase.storage.FirebaseStorage

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: MoreViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val storageRef = FirebaseStorage.getInstance().reference
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            if (uri != null) {
                storageRef.child(state.userProfile?.email ?: "test").putFile(uri)
                    .addOnSuccessListener {
                        Toast.makeText(context, "Image uploaded successfully", Toast.LENGTH_SHORT)
                            .show()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(context, "Upload failed: ${e.message}", Toast.LENGTH_SHORT)
                            .show()
                    }
            }
        }
    )
    LaunchedEffect(true) {
        viewModel.getUserProfile()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 32.dp, horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (state.isProfileLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(top = 32.dp))
        } else {
            if (state.profileError != "") {
                Text(text = state.profileError, color = MaterialTheme.colorScheme.error)
            } else {
                AnimatedContent(targetState = state.userProfile?.imageUrl) {
                    if (it == null) {
                        ProfileSymbol(
                            modifier = Modifier.size(128.dp),
                            firstName = state.userProfile?.firstName ?: "",
                            lastName = state.userProfile?.lastName ?: ""
                        )
                    } else {
                        // Image from URL
                    }
                }
                Spacer(Modifier.height(8.dp))
                Row(
                    modifier = Modifier.clickable {
                        launcher.launch("image/*")
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Image",
                        tint = MaterialTheme.colorScheme.onBackground
                    )

                    Text(
                        text = "Edit",
                        color = MaterialTheme.colorScheme.onBackground
                    )

                }

                Spacer(Modifier.height(16.dp))
                ProfileInfoItem(primaryName = "Email", secondaryName = state.userProfile!!.email)
                ProfileInfoItem(
                    primaryName = "Full Name",
                    secondaryName = state.userProfile.firstName + " " + state.userProfile.lastName
                )
                ProfileInfoItem(
                    primaryName = "username",
                    secondaryName = state.userProfile.userName
                )
                ProfileInfoItem(
                    primaryName = "Phone Number",
                    secondaryName = state.userProfile.phoneNumber
                )
                ProfileInfoItem(
                    primaryName = "Birth Date",
                    secondaryName = state.userProfile.birthday
                )
            }

        }


    }


}


@Preview
@Composable
private fun ProfileScreenPreview() {
    DevDashTheme {
        ProfileScreen()
    }
}