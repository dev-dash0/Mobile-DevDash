package com.elfeky.devdash.ui.screens.main_screens.more

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.elfeky.devdash.navigation.main_navigation.MainScreen
import com.elfeky.devdash.ui.screens.main_screens.more.components.IconAndTextMoreItem
import com.elfeky.devdash.ui.screens.main_screens.more.components.SureAlertDialog
import com.elfeky.devdash.ui.theme.DevDashTheme

@Composable
fun MoreScreen(
    modifier: Modifier = Modifier,
    mainNavController: NavController,
    appNavController: NavController
) {

    var showLogoutDialog by remember { mutableStateOf(false) }
    var showDeleteAccountDialog by remember { mutableStateOf(false) }


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
        ) {
            mainNavController.navigate(MainScreen.ProfileScreen.route)
        }
        HorizontalDivider()
        IconAndTextMoreItem(
            primaryText = "Update Password",
            secondaryText = "Reset your password",
            logo = Icons.Default.Lock,
            contentDescription = "Update Password",
        ) { }
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
        IconAndTextMoreItem(
            primaryText = "Logout",
            secondaryText = "End Session",
            logo = Icons.AutoMirrored.Filled.Logout,
            contentDescription = "Logout",
        ) {
            showLogoutDialog = true
        }



        if (showLogoutDialog) {
            SureAlertDialog(
                onDismiss = { showLogoutDialog = false },
                onConfirm = {
                    showLogoutDialog = false
                },
                action = "Logout"
            )
        }

        if (showDeleteAccountDialog) {
            SureAlertDialog(
                onDismiss = { showDeleteAccountDialog = false },
                onConfirm = {
                    showDeleteAccountDialog = false
                },
                action = "Delete Account"
            )
        }

    }
}

@Preview
@Composable
private fun MoreScreenPreview() {
    DevDashTheme {
        MoreScreen(
            appNavController = rememberNavController(),
            mainNavController = rememberNavController()
        )
    }
}