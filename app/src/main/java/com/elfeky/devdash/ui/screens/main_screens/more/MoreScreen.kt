package com.elfeky.devdash.ui.screens.main_screens.more

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.elfeky.devdash.ui.screens.main_screens.more.components.ChangePasswordDialog
import com.elfeky.devdash.ui.screens.main_screens.more.components.IconAndTextMoreItem
import com.elfeky.devdash.ui.screens.main_screens.more.components.SureAlertDialog
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.domain.model.ChangePasswordRequest
import com.elfeky.domain.model.LoginResponse

@Composable
fun MoreScreen(
    modifier: Modifier = Modifier,
    viewModel: MoreViewModel = hiltViewModel(),
    accessToken: String,
    refreshToken: String,
    onLogout: () -> Unit,
    onProfileNavigate: () -> Unit
) {
    var showChangePasswordDialog by remember { mutableStateOf(false) }
    var showLogoutDialog by remember { mutableStateOf(false) }
    var showDeleteAccountDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current

    val state = viewModel.state.value




    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 32.dp, horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconAndTextMoreItem(
            primaryText = "Personal Information",
            secondaryText = "Your information",
            logo = Icons.Default.ManageAccounts,
            contentDescription = "Personal Information",
            onItemClick = onProfileNavigate
        )
        HorizontalDivider()
        IconAndTextMoreItem(
            primaryText = "Update Password",
            secondaryText = "Reset your password",
            logo = Icons.Default.Lock,
            contentDescription = "Update Password",
        ) {
            showChangePasswordDialog = true
        }
        HorizontalDivider()
        IconAndTextMoreItem(
            primaryText = "Delete Account",
            secondaryText = "Remove your account",
            logo = Icons.Default.Delete,
            contentDescription = "Logout",
        ) {
            showDeleteAccountDialog = true
        }
        HorizontalDivider()
        Spacer(Modifier.height(16.dp))
        IconAndTextMoreItem(
            primaryText = "Logout",
            secondaryText = "End Session",
            logo = Icons.AutoMirrored.Filled.Logout,
            contentDescription = "Logout",
        ) {
            showLogoutDialog = true
        }

        if (showChangePasswordDialog) {
            ChangePasswordDialog(
                onDismiss = { showChangePasswordDialog = false },
                onConfirm = { currentPassword, newPassword ->
                    viewModel.changePassword(
                        accessToken = accessToken,
                        changePasswordRequest = ChangePasswordRequest(
                            currentPassword = currentPassword,
                            newPassword = newPassword
                        )
                    )
                },
                error = state.changePasswordError
            )
        }


        if (showLogoutDialog) {
            SureAlertDialog(
                onDismiss = { showLogoutDialog = false },
                onConfirm = {
                    viewModel.logout(
                        LoginResponse(
                            accessToken = accessToken,
                            refreshToken = refreshToken
                        )
                    )
                },
                action = "Logout",
                error = state.logoutError
            )
        }

        if (showDeleteAccountDialog) {
            SureAlertDialog(
                onDismiss = { showDeleteAccountDialog = false },
                onConfirm = {
                    viewModel.deleteAccount(accessToken)
                },
                action = "Delete Account",
                error = state.deleteAccountError
            )
        }

        if (state.passwordChanged) {
            showChangePasswordDialog = false
            LaunchedEffect(Unit) {
                Toast.makeText(context, "Password Updated", Toast.LENGTH_SHORT).show()
            }
        }


        if (state.isLoggedOut || state.isAccountDeleted) {
            showLogoutDialog = false
            showDeleteAccountDialog = false
            viewModel.eraseLoginResponse()
            onLogout()
        }
    }
}

@Preview
@Composable
private fun MoreScreenPreview() {
    DevDashTheme {
        MoreScreen(
            accessToken = "",
            refreshToken = "",
            onLogout = {},
            onProfileNavigate = {}
        )
    }
}