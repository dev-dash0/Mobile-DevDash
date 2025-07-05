package com.elfeky.devdash.ui.screens.main_screens.more.components.profile_screen

import android.net.Uri
import android.util.Log
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import com.elfeky.devdash.ui.screens.main_screens.more.MoreViewModel
import com.elfeky.devdash.ui.screens.main_screens.more.components.profile_screen.component.ProfileInfoItem
import com.elfeky.devdash.ui.screens.main_screens.more.components.profile_screen.component.ProfileSymbol
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.domain.model.account.UserProfileRequest
import com.google.firebase.storage.FirebaseStorage

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: MoreViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val storageRef = remember { FirebaseStorage.getInstance().reference }
    val context = LocalContext.current

    val isUpdatingImage = remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let {
                isUpdatingImage.value = true
                Toast.makeText(context, "Uploading the image", Toast.LENGTH_LONG)
                    .show()


                storageRef.child("ProfileImages/${state.userProfile?.email ?: "test"}")
                    .putFile(uri)
                    .addOnSuccessListener {
                        isUpdatingImage.value = false
                        Toast.makeText(
                            context,
                            "Image uploaded successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        // download image url
                        storageRef.child("ProfileImages/${state.userProfile?.email ?: "test"}")
                            .downloadUrl
                            .addOnSuccessListener { uri ->
                                Log.i("imageUrl", uri.toString())
                                viewModel.updateProfile(
                                    UserProfileRequest(
                                        imageUrl = uri.toString(),
                                        firstName = state.userProfile?.firstName,
                                        lastName = state.userProfile?.lastName,
                                        phoneNumber = state.userProfile?.phoneNumber,
                                        birthday = state.userProfile?.birthday,
                                        userName = state.userProfile?.userName
                                    )
                                )
                            }
                            .addOnFailureListener {
                                Toast.makeText(
                                    context,
                                    "failed to update image",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(
                            context,
                            "Upload failed: ${e.message}",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        isUpdatingImage.value = false
                    }


            }
        }
    )
    LaunchedEffect(Unit) {
        viewModel.getUserProfile()
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 32.dp, horizontal = 16.dp),
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (state.isProfileLoading || state.isUpdatingProfile) {
                CircularProgressIndicator(modifier = Modifier.padding(top = 32.dp))
            } else {
                if (state.profileError != "") {
                    Text(text = state.profileError, color = MaterialTheme.colorScheme.error)
                } else if (state.updateProfileError != "") {
                    Text(text = state.updateProfileError, color = MaterialTheme.colorScheme.error)
                } else {
                    AnimatedContent(targetState = state.userProfile?.imageUrl) {
                        if (it == null) {
                            ProfileSymbol(
                                modifier = Modifier.size(128.dp),
                                firstName = state.userProfile?.firstName ?: "",
                                lastName = state.userProfile?.lastName ?: "",
                                isUpdatingImage = isUpdatingImage
                            )
                        } else {
                            // Image from URL
                            SubcomposeAsyncImage(
                                model = it,
                                contentDescription = "Profile Image",
                                loading = { CircularProgressIndicator() },
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(128.dp)
                                    .clip(CircleShape)
                            )
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
                    ProfileInfoItem(
                        primaryName = "Email",
                        secondaryName = state.userProfile!!.email
                    )
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
}


@Preview
@Composable
private fun ProfileScreenPreview() {
    DevDashTheme {
        ProfileScreen()
    }
}