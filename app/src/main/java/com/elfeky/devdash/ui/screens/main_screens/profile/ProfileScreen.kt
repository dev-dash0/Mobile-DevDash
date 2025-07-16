package com.elfeky.devdash.ui.screens.main_screens.profile

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.elfeky.domain.model.account.UserProfileRequest
import com.google.firebase.storage.FirebaseStorage

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel(),
    onLogout: () -> Unit,
) {
    val state by viewModel.state
    val storageRef = remember { FirebaseStorage.getInstance().reference }
    val context = LocalContext.current

    val isUpdatingImage = remember { mutableStateOf(false) }

    when {
        state.isProfileLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        state.userProfile != null -> {
            ProfileContent(
                user = state.userProfile!!,
                modifier = modifier,
                onLogout = {
                    viewModel.logout()
                    onLogout()
                },
                onDeleteAccount = viewModel::deleteAccount,
                onChangePassword = viewModel::changePassword,
                onEditAccount = viewModel::updateProfile,
                onImageChanged = { uri ->
                    isUpdatingImage.value = true
                    if (uri != null) {
                        Toast.makeText(context, "Uploading the image", Toast.LENGTH_LONG).show()

                        storageRef
                            .child("ProfileImages/${state.userProfile?.email ?: "test"}")
                            .putFile(uri as Uri)
                            .addOnSuccessListener {
                                isUpdatingImage.value = false
                                Toast.makeText(
                                    context,
                                    "Image uploaded successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                                Log.i("imageUrl", it.toString())

                                storageRef.child("ProfileImages/${state.userProfile?.email ?: "test"}")
                                    .downloadUrl
                                    .addOnSuccessListener { newUri ->
                                        Log.i("imageUrl", newUri.toString())
                                        viewModel.updateProfile(
                                            UserProfileRequest(
                                                imageUrl = newUri.toString(),
                                                firstName = state.userProfile?.firstName,
                                                lastName = state.userProfile?.lastName,
                                                phoneNumber = state.userProfile?.phoneNumber,
                                                birthday = state.userProfile?.birthday,
                                                userName = state.userProfile?.userName,
                                                email = state.userProfile?.email
                                            )
                                        )
                                    }
                                    .addOnFailureListener {
                                        Log.d("ProfileScreen", "failed to update image")
                                        Toast.makeText(
                                            context,
                                            "failed to update image",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                            }
                            .addOnFailureListener { e ->
                                Log.d("ProfileScreen", "Upload failed")
                                Toast.makeText(
                                    context,
                                    "Upload failed: ${e.message}",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                                isUpdatingImage.value = false
                            }
                    } else {

                        storageRef.child("ProfileImages/${state.userProfile?.email ?: "test"}")
                            .delete()
                            .addOnSuccessListener {
                                isUpdatingImage.value = false
                                Toast.makeText(context, "Image removed", Toast.LENGTH_SHORT).show()
                                viewModel.updateProfile(
                                    UserProfileRequest(
                                        imageUrl = "",
                                        firstName = state.userProfile?.firstName,
                                        lastName = state.userProfile?.lastName,
                                        phoneNumber = state.userProfile?.phoneNumber,
                                        birthday = state.userProfile?.birthday,
                                        userName = state.userProfile?.userName,
                                        email = state.userProfile?.email
                                    )
                                )
                            }
                            .addOnFailureListener { e ->
                                isUpdatingImage.value = false
                                Toast.makeText(
                                    context,
                                    "Failed to remove image: ${e.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                                Log.d("ProfileScreen", "Failed to remove image", e)
                            }
                    }
                }
            )
        }
    }
}
