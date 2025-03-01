package com.elfeky.devdash.ui.screens.main_screens.more.components.profile_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.elfeky.devdash.ui.screens.main_screens.more.MoreViewModel
import com.elfeky.devdash.ui.screens.main_screens.more.components.profile_screen.component.ProfileInfoItem
import com.elfeky.devdash.ui.theme.DevDashTheme

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: MoreViewModel = hiltViewModel(),
    accessToken: String,
    refreshToken: String
) {
    val state = viewModel.state.value
    LaunchedEffect(true) {
        viewModel.getUserProfile(accessToken)
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

            ProfileInfoItem(primaryName = "Email", secondaryName = state.userProfile!!.email)
            ProfileInfoItem(
                primaryName = "Full Name",
                secondaryName = state.userProfile!!.firstName + " " + state.userProfile!!.lastName
            )
            ProfileInfoItem(primaryName = "username", secondaryName = state.userProfile!!.userName)
            ProfileInfoItem(primaryName = "Phone Number", secondaryName = "01000431490")
        }
        Text(text = state.profileError, color = MaterialTheme.colorScheme.error)

    }


}


@Preview
@Composable
private fun ProfileScreenPreview() {
    DevDashTheme {
        ProfileScreen(accessToken = "", refreshToken = "")
    }
}