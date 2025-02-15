package com.elfeky.devdash.ui.screens.done_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.elfeky.devdash.R
import com.elfeky.devdash.navigation.app_navigation.AppScreen
import com.elfeky.devdash.ui.theme.DevDashTheme
import com.elfeky.devdash.ui.utils.defaultButtonColor
import com.elfeky.devdash.ui.utils.gradientBackground

@Composable
fun DoneScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = gradientBackground
            )
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.complete_reset_password),
            contentDescription = "Password Changed",
            modifier = Modifier
                .size(128.dp)
                .clip(CircleShape)
        )
        Spacer(Modifier.height(32.dp))
        Text(
            text = "You are All Done !",
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = "Your password has been updated",
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 16.sp
        )
        Spacer(Modifier.height(32.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            onClick = {
                navController.navigate(AppScreen.SignInScreen.route)
            },
            colors = defaultButtonColor,
            shape = RoundedCornerShape(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = "Go to Sign In"
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = "Go To Sign In"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DoneScreenPreview() {
    DevDashTheme {
        DoneScreen(navController = rememberNavController())
    }
}